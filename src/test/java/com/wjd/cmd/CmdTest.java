package com.wjd.cmd;

import org.junit.Test;

public class CmdTest {

    @Test
    public void parse() {
        String[] args = new String[]{ "com.wjd.cmd.Cmd", "-classpath", "/projects" };
        Cmd cmd = new Cmd();
        cmd.parse(args);
    }

    @Test
    public void printHelp() {
        Cmd cmd = new Cmd();
        cmd.printHelp();
    }
}
