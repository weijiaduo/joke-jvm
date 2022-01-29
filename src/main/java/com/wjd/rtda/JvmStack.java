package com.wjd.rtda;

/**
 * 虚拟机栈
 */
public class JvmStack {

    /** 最大栈深度 */
    private int maxSize;
    /** 当前栈深度 */
    private int size;
    /** 栈顶对象 */
    private Frame top;

    public JvmStack(int maxSize) {
        this.maxSize = maxSize;
    }

    public void push(Frame frame) {
        if (size >= maxSize) {
            throw new StackOverflowError("jvm stack is overflow: " + maxSize);
        }
        frame.setLower(top);
        top = frame;
        size++;
    }

    public Frame pop() {
        if (size == 0) {
            throw new IllegalStateException("jvm stack is empty!");
        }
        Frame val = top;
        top = top.getLower();
        val.setLower(null);
        size--;
        return val;
    }

    public Frame top() {
        if (size == 0) {
            throw new IllegalStateException("jvm stack is empty!");
        }
        return top;
    }

}
