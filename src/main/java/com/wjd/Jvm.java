package com.wjd;

import com.wjd.classpath.Classpath;
import com.wjd.cmd.Cmd;
import com.wjd.rtda.Thread;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.ClassMetaLoader;
import com.wjd.rtda.meta.MethodMeta;
import com.wjd.rtda.meta.ShimClassMeta;
import com.wjd.rtda.meta.StringPool;
import com.wjd.rtda.stack.Frame;

import java.nio.file.Paths;

public class Jvm {

    /** 命令行 */
    private Cmd cmd;
    /** 类文件路径 */
    private Classpath classpath;
    /** 类加载器 */
    private ClassMetaLoader loader;
    /** 主线程 */
    private Thread mainThread;
    /** JVM 参数 */
    private JvmOptions jvmOptions;

    public static void main(String[] args) {
        Cmd.printHelp();
        String[] testArgs = new String[]{
                "com.wjd.rtda.CloneTest",
                "-Xjre", "D:\\Environment\\dev\\jdk\\jdk1.8.0_25_x64\\jre",
                "-classpath", "D:\\Projects\\IdeaProjects\\joke-jvm\\target\\test-classes;D:\\Projects\\IdeaProjects\\joke-jvm\\target\\classes"
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
        init();
        run();
    }

    /**
     * 初始化虚拟机
     */
    private void init() {
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
    private void run() {
        // 入口函数和参数
        String mainClassName = cmd.getMainClass().replaceAll("\\.", "/");
        HeapObject mainClassObj = StringPool.getStringObj(loader, mainClassName);
        String[] args = cmd.getArgs();
        HeapObject argsObj = StringPool.createStringArray(loader, args);

        // 初始化启动线程栈帧，调用bootstrap命令
        MethodMeta bootstrapMethod = ShimClassMeta.getInstance().getBootStrapMethod();
        bootstrapMethod.getClassMeta().setLoader(loader);
        Frame frame = mainThread.newFrame(bootstrapMethod);
        mainThread.pushFrame(frame);
        frame.getLocalVars().setRef(0, mainClassObj);
        frame.getLocalVars().setRef(1, argsObj);

        // 解释执行字节码指令
        Interpreter.interpret(mainThread, cmd.isVerboseInstFlag());
    }

}
