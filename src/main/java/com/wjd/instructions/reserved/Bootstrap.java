package com.wjd.instructions.reserved;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.instructions.references.InitClass;
import com.wjd.rtda.Thread;
import com.wjd.rtda.heap.Heap;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.ClassMetaLoader;
import com.wjd.rtda.meta.MethodMeta;
import com.wjd.rtda.meta.StringPool;
import com.wjd.rtda.stack.Frame;

/**
 * 启动指令
 * @since 2022/2/15
 */
public class Bootstrap extends NoOperandsInstruction {

    private static final Bootstrap instance = new Bootstrap();

    public static Bootstrap getInstance() {
        return instance;
    }

    private ClassMeta mainClass;
    private String mainClassName;
    private HeapObject argsObj;

    private ClassMetaLoader bootLoader;
    private final String[] bootClassNames = new String[] {
            "java/lang/Class",
            "java/lang/String",
            "java/lang/System",
            "java/lang/Thread",
            "java/lang/ThreadGroup",
            "java/io/PrintStream"
    };

    @Override
    public void execute(Frame frame) {
        if (bootLoader == null) {
            init(frame);
        }

        // 反复执行初始化类的操作，直到都初始化完成为止
        Thread thread = frame.getThread();
        if (!initBootClass(thread) ||
                !initMainThread(thread) ||
                !initSystem(thread) ||
                !initMainClass(thread)) {
            return;
        }

        // 初始化结束后，开始执行主入口函数
        execMain(thread);
    }

    private void init(Frame frame) {
        bootLoader = frame.getMethod().getClassMeta().getLoader();
        HeapObject mainClassObj = frame.getLocalVars().getRef(0);
        mainClassName = StringPool.getRawString(mainClassObj);
        argsObj = frame.getLocalVars().getRef(1);
    }

    /**
     * 初始化一些必要的类
     */
    private boolean initBootClass(Thread thread) {
        for (String className : bootClassNames) {
            ClassMeta classMeta = bootLoader.loadClass(className);
            if (!classMeta.isInitStarted()) {
                thread.revertNextPc();
                InitClass.initClass(thread, classMeta);
                return false;
            }
        }
        return true;
    }

    /**
     * 初始化主线程
     */
    private boolean initMainThread(Thread thread) {
        Frame frame = thread.currentFrame();
        ClassMetaLoader loader = bootLoader;

        if (thread.getJThreadGroup() == null) {
            // java/lang/ThreadGroup
            thread.revertNextPc();

            ClassMeta threadGroupClass = loader.loadClass("java/lang/ThreadGroup");
            HeapObject mainThreadGroupObj = Heap.newObject(threadGroupClass);
            thread.setJThreadGroup(mainThreadGroupObj);

            MethodMeta initMethod = threadGroupClass.getConstructor("()V");
            frame.getOpStack().pushRef(mainThreadGroupObj); // this
            thread.invokeMethod(initMethod);
            return false;
        }

        if (thread.getJThread() == null) {
            // java/lang/Thread
            thread.revertNextPc();

            ClassMeta threadClass = loader.loadClass("java/lang/Thread");
            HeapObject mainThreadObj = Heap.newObject(threadClass);
            mainThreadObj.setFieldInt("priority", "I", 1);
            thread.setJThread(mainThreadObj);

            MethodMeta initMethod = threadClass.getConstructor("(Ljava/lang/ThreadGroup;Ljava/lang/String;)V");
            frame.getOpStack().pushRef(mainThreadObj);                           // this
            frame.getOpStack().pushRef(thread.getJThreadGroup());                // group
            frame.getOpStack().pushRef(StringPool.getStringObj(loader, "main")); // name
            thread.invokeMethod(initMethod);
            return false;
        }

        return true;
    }

    /**
     * 初始化系统属性
     */
    private boolean initSystem(Thread thread) {
        ClassMeta sysClass = bootLoader.loadClass("java/lang/System");
        HeapObject props = sysClass.getFieldRef("props", "Ljava/util/Properties;");
        if (props == null) {
            thread.revertNextPc();
            MethodMeta initSysMethod = sysClass.getStaticMethod("initializeSystemClass", "()V");
            thread.invokeMethod(initSysMethod);
            return false;
        }
        return true;
    }

    /**
     * 初始化主入口类
     */
    private boolean initMainClass(Thread thread) {
        mainClass = bootLoader.loadClass(mainClassName);
        if (!mainClass.isInitStarted()) {
            thread.revertNextPc();
            InitClass.initClass(thread, mainClass);
            return false;
        }
        return true;
    }

    /**
     * 执行主入口函数
     */
    private void execMain(Thread thread) {
        thread.popFrame(); // bootstrap frame

        MethodMeta mainMethod = mainClass.getMainMethod();
        if (mainMethod == null) {
            System.out.println("Not found main method in class: " + mainClass.getJavaName());
            return;
        }

        Frame frame = thread.newFrame(mainMethod);
        thread.pushFrame(frame);
        frame.getLocalVars().setRef(0, argsObj);
    }

}
