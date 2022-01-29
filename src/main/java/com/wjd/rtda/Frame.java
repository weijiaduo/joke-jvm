package com.wjd.rtda;

/**
 * 栈帧
 */
public class Frame {

    /** 局部变量表 */
    private LocalVars localvars;
    /** 操作数栈 */
    private OperandStack operandStack;
    /** 指向下一个栈帧 */
    private Frame lower;

    public Frame(int maxLocals, int maxStack) {
        localvars = new LocalVars(maxLocals);
        operandStack = new OperandStack(maxStack);
    }

    public Frame getLower() {
        return lower;
    }

    public void setLower(Frame lower) {
        this.lower = lower;
    }

    public LocalVars getLocalvars() {
        return localvars;
    }

    public OperandStack getOperandStack() {
        return operandStack;
    }
}
