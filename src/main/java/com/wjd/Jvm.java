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
                "com.wjd.rtda.CloneTest", "-classpath",
                "D:\\Projects\\IdeaProjects\\j-jvm\\target\\test-classes;D:\\Projects\\IdeaProjects\\j-jvm\\target\\classes"
        };

        Jvm jvm = new Jvm(testArgs);
        jvm.start();
    }

    public Jvm(String[] args) {
        cmd = Cmd.parseFrom(args);
        Classpath.verbosePath = cmd.isVerboseClassPathFlag();
        classpath = new Classpath(cmd.getJreOption(), cmd.getCpOption());
        loader = ClassMetaLoader.newClassLoader(classpath, cmd.isVerboseClassFlag());
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
        mainThread = new Thread(jvmOptions);
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

        // 初始化启动线程栈帧，调用bootstrap命令
        MethodMeta bootstrapMethod = ShimClassMeta.getInstance().getBootStrapMethod();
        bootstrapMethod.getClazz().setLoader(loader);
        Frame frame = mainThread.newFrame(bootstrapMethod);
        mainThread.pushFrame(frame);
        frame.getLocalVars().setRef(0, mainClassObj);
        frame.getLocalVars().setRef(1, argsObj);

        // 解释执行字节码指令
        Interpreter.interpret(mainThread, cmd.isVerboseInstFlag());
    }

}
