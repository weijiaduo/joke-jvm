package com.wjd.rtda;

import com.wjd.rtda.meta.MethodMeta;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.stack.JvmStack;

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

    public Frame topFrame() {
        return stack.top();
    }

    public Frame currentFrame() {
        return stack.top();
    }

    public Frame newFrame(MethodMeta methodMeta) {
        return new Frame(this, methodMeta);
    }

    public Frame[] getFrames() {
        return stack.getFrames();
    }

    public boolean isStackEmpty() {
        return stack.isEmpty();
    }

    public void clearStack() {
        stack.clear();
    }

}
