package com.wjd.rtda;

import com.wjd.rtda.heap.member.Method;

/**
 * 栈帧
 */
public class Frame {

    /** 指向下一个栈帧 */
    private Frame lower;

    /** 所属线程 */
    private Thread thread;
    /** 所属方法 */
    private Method method;
    /** 最大本地变量数量 */
    private int maxLocals;
    /** 方法栈深度 */
    private int maxStack;
    /** 局部变量表 */
    private LocalVars localVars;
    /** 操作数栈 */
    private OperandStack operandStack;
    /** 下一条执行的指令索引 */
    private int nextPc;

    public Frame(Thread thread, Method method) {
        this.thread = thread;
        this.method = method;
        maxLocals = method.getMaxLocals();
        maxStack = method.getMaxStacks();
        localVars = new LocalVars(maxLocals);
        operandStack = new OperandStack(maxStack);
    }

    public Thread getThread() {
        return thread;
    }

    public Method getMethod() {
        return method;
    }

    public Frame getLower() {
        return lower;
    }

    public void setLower(Frame lower) {
        this.lower = lower;
    }

    public LocalVars getLocalVars() {
        return localVars;
    }

    public OperandStack getOperandStack() {
        return operandStack;
    }

    public int getNextPc() {
        return nextPc;
    }

    public void setNextPc(int nextPc) {
        this.nextPc = nextPc;
    }

    public void revertNextPc() {
        nextPc = thread.getPc();
    }

    @Override
    public String toString() {
        return "Frame{" +
                "localVars=" + localVars +
                ", operandStack=" + operandStack +
                ", nextPc=" + nextPc +
                '}';
    }
}
