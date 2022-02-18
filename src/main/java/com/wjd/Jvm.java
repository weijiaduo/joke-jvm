package com.wjd;

import com.wjd.cmd.Cmd;
import com.wjd.cp.Classpath;
import com.wjd.rtda.Thread;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.*;
import com.wjd.rtda.stack.Frame;

import java.nio.file.Paths;

public class Jvm {

    private Cmd cmd;
    private Classpath classpath;
    private ClassMetaLoader loader;
    private Thread mainThread;
    private JvmOptions jvmOptions;

    public static void main(String[] args) {
        Cmd cmd = new Cmd();
        cmd.printHelp();

        String[] testArgs = new String[] {
                "com.wjd.rtda.HelloWorld", "-classpath",
                "D:\\Projects\\IdeaProjects\\self-jvm\\target\\test-classes;D:\\Projects\\IdeaProjects\\self-jvm\\target\\classes"
        };
        cmd.parse(testArgs);

        Jvm jvm = newJvm(cmd);
        jvm.start();
    }

    public static Jvm newJvm(Cmd cmd) {
        Jvm jvm = new Jvm();
        jvm.cmd = cmd;
        Classpath.verbosePath = cmd.isVerboseClassPathFlag();
        jvm.classpath = new Classpath(cmd.getJreOption(), cmd.getCpOption());
        jvm.loader = ClassMetaLoader.newClassLoader(jvm.classpath, cmd.isVerboseClassFlag());
        jvm.mainThread = new Thread();
        return jvm;
    }

    /**
     * 启动虚拟机
     */
    public void start() {
        initVM();
        execMain();
    }

    /**
     * 初始化虚拟机
     */
    private void initVM() {
        initOptions();
        mainThread.setJvmOptions(jvmOptions);
    }

    /**
     * 初始化虚拟机配置项
     */
    private void initOptions() {
        jvmOptions = new JvmOptions();
        jvmOptions.setAbsJavaHome(classpath.getJrePath());
        jvmOptions.setAbsJreLib(Paths.get(jvmOptions.getAbsJavaHome(), "lib").toAbsolutePath() + "");
        jvmOptions.setClasspath(classpath.getClassPath());
    }

    /**
     * 执行主入口函数
     */
    private void execMain() {
        // 入口函数和参数
        String mainClassName = cmd.getMainClass().replaceAll("\\.", "/");
        HeapObject mainClassObj = StringPool.getObjString(loader, mainClassName);
        String[] args = cmd.getArgs();
        HeapObject argsObj = createArgsArray(loader, args);

        // 调用bootstrap命令
        MethodMeta bootstrapMethod = ShimClassMeta.getInstance().getBootStrapMethod();
        bootstrapMethod.getClazz().setLoader(loader);
        Frame frame = mainThread.newFrame(bootstrapMethod);
        mainThread.pushFrame(frame);
        frame.getLocalVars().setRef(0, mainClassObj);
        frame.getLocalVars().setRef(1, argsObj);

        new Interpreter().interpret(mainThread, cmd.isVerboseInstFlag());
    }

    /**
     * 创建参数对象
     */
    private HeapObject createArgsArray(ClassMetaLoader loader, String[] args) {
        ClassMeta stringClassMeta = loader.loadClass("java/lang/String");
        HeapObject argsArray = stringClassMeta.getArrayClass().newArray(args.length);
        HeapObject[] refs = argsArray.getRefs();
        for (int i = 0; i < args.length; i++) {
            refs[i] = StringPool.getObjString(loader, args[i]);
        }
        return argsArray;
    }

}
