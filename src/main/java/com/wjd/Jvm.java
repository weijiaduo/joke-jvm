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
        Cmd.printHelp();
        String[] testArgs = new String[] {
                "com.wjd.rtda.BoxTest", "-classpath",
                "D:\\Projects\\IdeaProjects\\self-jvm\\target\\test-classes;D:\\Projects\\IdeaProjects\\self-jvm\\target\\classes"
        };

        Jvm jvm = newJvm(testArgs);
        jvm.start();
    }

    public static Jvm newJvm(String[] args) {
        Jvm jvm = new Jvm();
        jvm.cmd = Cmd.parseFrom(args);
        Classpath.verbosePath = jvm.cmd.isVerboseClassPathFlag();
        jvm.classpath = new Classpath(jvm.cmd.getJreOption(), jvm.cmd.getCpOption());
        jvm.loader = ClassMetaLoader.newClassLoader(jvm.classpath, jvm.cmd.isVerboseClassFlag());
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
        HeapObject mainClassObj = StringPool.getStringObj(loader, mainClassName);
        String[] args = cmd.getArgs();
        HeapObject argsObj = StringPool.createStringArray(loader, args);

        // 调用bootstrap命令
        MethodMeta bootstrapMethod = ShimClassMeta.getInstance().getBootStrapMethod();
        bootstrapMethod.getClazz().setLoader(loader);
        Frame frame = mainThread.newFrame(bootstrapMethod);
        mainThread.pushFrame(frame);
        frame.getLocalVars().setRef(0, mainClassObj);
        frame.getLocalVars().setRef(1, argsObj);

        Interpreter.interpret(mainThread, cmd.isVerboseInstFlag());
    }

}
