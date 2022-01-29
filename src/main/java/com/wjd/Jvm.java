package com.wjd;

import com.wjd.classfile.ClassFile;
import com.wjd.classfile.ClassReader;
import com.wjd.classfile.member.MethodInfo;
import com.wjd.cmd.Cmd;
import com.wjd.cp.Classpath;

import java.io.IOException;

public class Jvm {

    public static void main(String[] args) throws IOException {
        Cmd cmd = new Cmd();
        cmd.printHelp();

        String[] testArgs = new String[]{ "com.wjd.cmd.Cmd", "-classpath",
                "D:\\Projects\\IdeaProjects\\self-jvm\\target\\test-classes;D:\\Projects\\IdeaProjects\\self-jvm\\target\\classes" };
        cmd.parse(testArgs);

        String userClassName = "com\\wjd\\instructions\\InstructionsTest";
        Classpath classpath = new Classpath(cmd.getJreOption(), cmd.getCpOption());

        ClassFile classFile = loadClass(userClassName, classpath);
        MethodInfo mainMethod = getMainMethod(classFile);
        if (mainMethod != null) {
            new Interpreter().interpret(mainMethod);
        } else {
            System.out.println("Not found main method!");
        }
    }

    /**
     * 加载类
     */
    public static ClassFile loadClass(String className, Classpath classpath) throws IOException {
        byte[] userClassBytes = classpath.readClass(className);
        ClassReader reader = new ClassReader(userClassBytes);
        return ClassFile.parse(reader);
    }

    /**
     * 获取类文件中的main方法
     */
    public static MethodInfo getMainMethod(ClassFile classFile) {
        for (MethodInfo m : classFile.getMethods())
            if ("main".equals(m.name()) && "([Ljava/lang/String;)V".equals(m.descriptor())) {
                return m;
            }
        return null;
    }

}
