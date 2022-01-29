package com.wjd;

import com.wjd.cmd.Cmd;
import com.wjd.cp.Classpath;

import java.io.IOException;

public class Jvm {

    public static void main(String[] args) throws IOException {
        Cmd cmd = new Cmd();
        cmd.printHelp();

        String[] testArgs = new String[]{ "com.wjd.cmd.Cmd", "-classpath", "C:\\Users\\weijiaduo\\IdeaProjects\\self-jvm\\target;C:\\Users\\weijiaduo\\IdeaProjects\\self-jvm\\target\\classes" };
        cmd.parse(testArgs);

        String bootClassName = "java\\lang\\Object";
        String userClassName = "com\\wjd\\cmd\\Cmd";
        Classpath classpath = new Classpath(cmd.getJreOption(), cmd.getCpOption());
        classpath.readClass(bootClassName);
        classpath.readClass(userClassName);
    }

}
