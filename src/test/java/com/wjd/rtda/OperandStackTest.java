package com.wjd.rtda;

import org.junit.Test;

import static org.junit.Assert.*;

public class OperandStackTest {

    @Test
    public void testOperandStack() {
        OperandStack stack = new OperandStack(100);
        stack.pushInt(100);
        stack.pushInt(-100);
        stack.pushLong(2997924580L);
        stack.pushLong(-2997924580L);
        stack.pushFloat(3.1415926F);
        stack.pushFloat(-3.1415926F);
        stack.pushDouble(2.71828182845);
        stack.pushDouble(-2.71828182845);
        stack.pushRef(this);
        System.out.println(stack.popRef());
        System.out.println(stack.popDouble());
        System.out.println(stack.popDouble());
        System.out.println(stack.popFloat());
        System.out.println(stack.popFloat());
        System.out.println(stack.popLong());
        System.out.println(stack.popLong());
        System.out.println(stack.popInt());
        System.out.println(stack.popInt());
    }

}