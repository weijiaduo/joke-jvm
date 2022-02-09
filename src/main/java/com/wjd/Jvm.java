package com.wjd;

import com.wjd.cmd.Cmd;
import com.wjd.cp.Classpath;
import com.wjd.rtda.heap.Class;
import com.wjd.rtda.heap.ClassLoader;
import com.wjd.rtda.heap.member.Method;

public class Jvm {

    public static void main(String[] args) {
        Cmd cmd = new Cmd();
        cmd.printHelp();

        String[] testArgs = new String[]{ "com.wjd.cmd.Cmd", "-verboseClass", "-verboseInst", "-classpath",
                "D:\\Projects\\IdeaProjects\\self-jvm\\target\\test-classes;D:\\Projects\\IdeaProjects\\self-jvm\\target\\classes" };
        cmd.parse(testArgs);

        String testClassName = "com\\wjd\\rtda\\FibonacciTest";
        Classpath classpath = new Classpath(cmd.getJreOption(), cmd.getCpOption());

        ClassLoader classLoader = ClassLoader.newClassLoader(classpath, cmd.isVerboseClassFlag());
        Class mainClass = classLoader.loadClass(testClassName);
        Method mainMethod = mainClass.getMainMethod();
        if (mainMethod != null) {
            new Interpreter().interpret(mainMethod, cmd.isVerboseInstFlag());
        } else {
            System.out.println("Not found main method!");
        }
    }

}
