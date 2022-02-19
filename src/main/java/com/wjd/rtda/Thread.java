package com.wjd.rtda;

import com.wjd.JvmOptions;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.MethodMeta;
import com.wjd.rtda.meta.ShimClassMeta;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.stack.JvmStack;

/**
 * 线程
 */
public class Thread {

    /** 线程组对象java/lang/ThreadGroup */
    private HeapObject jThreadGroup;
    /** 线程对象java/lang/Thread */
    private HeapObject jThread;

    /** 虚拟机启动配置 */
    private JvmOptions jvmOptions;

    /** 程序计数器  */
    private int pc;
    /** 虚拟机栈 */
    private JvmStack stack;

    public Thread(JvmOptions jvmOptions) {
        this.jvmOptions = jvmOptions;
        stack = new JvmStack(jvmOptions.getMaxStackSize());
    }

    public JvmOptions getJvmOptions() {
        return jvmOptions;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    /**
     * 回退指令地址，下次继续执行当前指令
     */
    public void revertNextPc() {
        currentFrame().revertNextPc();
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

    public Frame[] getFrames() {
        return stack.getFrames();
    }

    public Frame getTopFrameN(int i) {
        return stack.getTopFrameN(i);
    }

    public boolean isStackEmpty() {
        return stack.isEmpty();
    }

    public void clearStack() {
        stack.clear();
    }

    public Frame newFrame(MethodMeta method) {
        return new Frame(this, method);
    }

    public Frame newShimFrame(Slot[] args) {
        MethodMeta method = ShimClassMeta.getInstance().getReturnMethod();
        Frame frame = new Frame(this, method);
        if (args != null) {
            for (Slot slot : args) {
                frame.getOpStack().pushSlot(slot);
            }
        }
        return frame;
    }

    /**
     * 调用方法后，执行一个空的return方法
     * @param method 方法
     * @param args 参数
     */
    public void invokeMethodWithShim(MethodMeta method, Slot[] args) {
        Frame shimFrame = newShimFrame(args);
        this.pushFrame(shimFrame);
        this.invokeMethod(method);
    }

    /**
     * 调用方法
     * @param method 方法
     */
    public void invokeMethod(MethodMeta method) {
        Frame currentFrame = this.currentFrame();
        Frame newFrame = this.newFrame(method);
        this.pushFrame(newFrame);
        int argsSlotCount = method.getArgSlotCount();
        if (argsSlotCount > 0) {
            passArgs(currentFrame, newFrame, argsSlotCount);
        }
    }

    /**
     * 传递参数
     * @param from 上一个方法帧
     * @param to 下一个方法帧
     * @param argsSlotCount 参数插槽数量
     */
    private void passArgs(Frame from, Frame to, int argsSlotCount) {
        for (int i = argsSlotCount - 1; i >= 0; i--) {
            Slot slot = from.getOpStack().popSlot();
            to.getLocalVars().setSlot(i, slot);
        }
    }

    public HeapObject getJThreadGroup() {
        return jThreadGroup;
    }

    public void setJThreadGroup(HeapObject jThreadGroup) {
        this.jThreadGroup = jThreadGroup;
    }

    public HeapObject getJThread() {
        return jThread;
    }

    public void setJThread(HeapObject jThread) {
        this.jThread = jThread;
    }
}
