package com.wjd.instructions.reserved;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.instructions.references.InitClass;
import com.wjd.rtda.Slot;
import com.wjd.rtda.Thread;
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

    private static Bootstrap instance = new Bootstrap();

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
        bootLoader = frame.getMethod().getClazz().getLoader();
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
                thread.undoExec();
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

        if (thread.getjThreadGroup() == null) {
            // java/lang/ThreadGroup
            thread.undoExec();

            ClassMeta threadGroupClass = loader.loadClass("java/lang/ThreadGroup");
            HeapObject mainThreadGroupObj = threadGroupClass.newObject();
            thread.setjThreadGroup(mainThreadGroupObj);

            MethodMeta initMethod = threadGroupClass.getConstructor("()V");
            frame.getOperandStack().pushRef(mainThreadGroupObj); // this
            thread.invokeMethod(initMethod);
            return false;
        }

        if (thread.getjThread() == null) {
            // java/lang/Thread
            thread.undoExec();

            ClassMeta threadClass = loader.loadClass("java/lang/Thread");
            HeapObject mainThreadObj = threadClass.newObject();
            Slot prioritySlot = new Slot();
            Slot.setInt(prioritySlot, 1);
            mainThreadObj.setFieldValue("priority", "I", prioritySlot);
            thread.setjThread(mainThreadObj);

            MethodMeta initMethod = threadClass.getConstructor("(Ljava/lang/ThreadGroup;Ljava/lang/String;)V");
            frame.getOperandStack().pushRef(mainThreadObj);                           // this
            frame.getOperandStack().pushRef(thread.getjThreadGroup());                // group
            frame.getOperandStack().pushRef(StringPool.getObjString(loader, "main")); // name
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
        HeapObject props = sysClass.getRefVar("props", "Ljava/util/Properties;");
        if (props == null) {
            thread.undoExec();
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
            thread.undoExec();
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

        MethodMeta mainMethodMeta = mainClass.getMainMethod();
        if (mainMethodMeta == null) {
            System.out.println("Not found main method in class: " + mainClass.getJavaName());
            return;
        }

        Frame frame = thread.newFrame(mainMethodMeta);
        thread.pushFrame(frame);
        frame.getLocalVars().setRef(0, argsObj);
    }

}