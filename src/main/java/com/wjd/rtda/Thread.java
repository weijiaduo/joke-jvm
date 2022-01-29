package com.wjd.rtda;

/**
 * 线程
 */
public class Thread {

    /** 最大栈深度 */
    private static int maxStackSize = 1024;

    /** 程序计数器  */
    private int pc;
    /** 虚拟机栈 */
    private JvmStack stack;

    public Thread() {
        stack = new JvmStack(maxStackSize);
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public void pushFrame(Frame frame) {
        stack.push(frame);
    }

    public Frame popFrame() {
        return stack.pop();
    }

    public Frame currentFrame() {
        return stack.top();
    }

    public Frame newFrame(int maxLocals, int maxStack) {
        return new Frame(this, maxLocals, maxStack);
    }
}
