package com.wjd.classfile;

import com.wjd.classpath.Classpath;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ClassFileTest {

    @Test
    public void getClassName() throws IOException {
        String jreOption = null;
        String cpOption = "C:\\Users\\weijiaduo\\IdeaProjects\\joke-jvm\\target;C:\\Users\\weijiaduo\\IdeaProjects\\joke-jvm\\target\\classes";
        String bootClassName = "java\\lang\\Object";
        String userClassName = "com\\wjd\\classfile\\ClassFileStructureTest";
        Classpath classpath = new Classpath(jreOption, cpOption);

        byte[] bootClassBytes = classpath.readClass(bootClassName);
        ClassFile bootClassFile = ClassFile.parse(bootClassBytes);
        assertEquals("Class name error", "java/lang/Object", bootClassFile.getClassName());

        byte[] userClassBytes = classpath.readClass(userClassName);
        ClassFile userClassFile = ClassFile.parse(userClassBytes);
        assertEquals("Class name error", "com/wjd/classfile/ClassFileStructureTest", userClassFile.getClassName());
    }

}