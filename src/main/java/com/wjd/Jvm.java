package com.wjd;

import com.wjd.cmd.Cmd;
import com.wjd.cp.Classpath;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.heap.ClassLoader;
import com.wjd.rtda.meta.MethodMeta;

public class Jvm {

    public static void main(String[] args) {
        Cmd cmd = new Cmd();
        cmd.printHelp();

        String[] testArgs = new String[]{ "com.wjd.cmd.Cmd", "-classpath",
                "D:\\Projects\\IdeaProjects\\self-jvm\\target\\test-classes;D:\\Projects\\IdeaProjects\\self-jvm\\target\\classes" };
        cmd.parse(testArgs);

        String testClassName = "com\\wjd\\rtda\\BoxTest";
        Classpath classpath = new Classpath(cmd.getJreOption(), cmd.getCpOption());
        Classpath.verbosePath = cmd.isVerboseClassPathFlag();

        ClassLoader classLoader = ClassLoader.newClassLoader(classpath, cmd.isVerboseClassFlag());
        ClassMeta mainClassMeta = classLoader.loadClass(testClassName);
        MethodMeta mainMethodMeta = mainClassMeta.getMainMethod();
        if (mainMethodMeta != null) {
            new Interpreter().interpret(mainMethodMeta, cmd.isVerboseInstFlag(), cmd.getArgs());
        } else {
            System.out.println("Not found main method!");
        }
    }

}
