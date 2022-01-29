package com.wjd.cp;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ClasspathTest {

    @Test
    public void readClass() throws IOException {
        String jreOption = null;
        String cpOption = "C:\\Users\\weijiaduo\\IdeaProjects\\self-jvm\\target;C:\\Users\\weijiaduo\\IdeaProjects\\self-jvm\\target\\classes";
        String bootClassName = "java\\lang\\Object";
        String userClassName = "com\\wjd\\cmd\\Cmd";
        Classpath classpath = new Classpath(jreOption, cpOption);
        Assert.assertNotNull("Object is null", classpath.readClass(bootClassName));
        Assert.assertNotNull("Cmd is null", classpath.readClass(userClassName));
    }

    @Test
    public void string() {
    }
}