package com.wjd.classfile;

import com.wjd.cp.Classpath;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import static org.junit.Assert.*;

public class ClassFileTest {

    @Test
    public void getClassName() throws IOException {
        String jreOption = null;
        String cpOption = "C:\\Users\\weijiaduo\\IdeaProjects\\self-jvm\\target;C:\\Users\\weijiaduo\\IdeaProjects\\self-jvm\\target\\classes";
        String bootClassName = "java\\lang\\Object";
        String userClassName = "com\\wjd\\classfile\\ClassFileStructureTest";
        Classpath classpath = new Classpath(jreOption, cpOption);

        byte[] bootClassBytes = classpath.readClass(bootClassName);
        ClassReader bootReader = new ClassReader(bootClassBytes);
        ClassFile bootClassFile = ClassFile.parse(bootReader);
        assertEquals("Class name error", "java/lang/Object", bootClassFile.getClassName());

        byte[] userClassBytes = classpath.readClass(userClassName);
        ClassReader userReader = new ClassReader(userClassBytes);
        ClassFile userClassFile = ClassFile.parse(userReader);
        assertEquals("Class name error", "com/wjd/classfile/ClassFileStructureTest", userClassFile.getClassName());
    }

    @Test
    public void etstt() {
        int a = 1;
        a >>>= 1;
        boolean s = true;
        s &= false;
        double d = 1.0;
    }
}