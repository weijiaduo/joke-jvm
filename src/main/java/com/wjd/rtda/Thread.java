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

    /** 最大栈深度 */
    private static int maxStackSize = 1024;

    private HeapObject jThreadGroup;
    private HeapObject jThread;
    private int priority;

    /** 虚拟机启动配置 */
    private JvmOptions jvmOptions;
    /** 程序计数器  */
    private int pc;
    /** 虚拟机栈 */
    private JvmStack stack;

    public Thread() {
        stack = new JvmStack(maxStackSize);
    }

    public JvmOptions getJvmOptions() {
        return jvmOptions;
    }

    public void setJvmOptions(JvmOptions jvmOptions) {
        this.jvmOptions = jvmOptions;
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

    public Frame newFrame(MethodMeta methodMeta) {
        return new Frame(this, methodMeta);
    }

    public Frame newShimFrame(Slot[] args) {
        MethodMeta methodMeta = ShimClassMeta.getInstance().getReturnMethod();
        Frame frame = new Frame(this, methodMeta);
        if (args != null) {
            for (Slot slot : args) {
                frame.getOperandStack().pushSlot(slot);
            }
        }
        return frame;
    }

    public void invokeMethodWithShim(MethodMeta methodMeta, Slot[] args) {
        Frame shimFrame = newShimFrame(args);
        this.pushFrame(shimFrame);
        this.invokeMethod(methodMeta);
    }

    /**
     * 调用方法
     * @param methodMeta 方法
     */
    public void invokeMethod(MethodMeta methodMeta) {
        Frame currentFrame = this.currentFrame();
        Frame newFrame = this.newFrame(methodMeta);
        this.pushFrame(newFrame);
        int argsSlotCount = methodMeta.getArgSlotCount();
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
            Slot slot = from.getOperandStack().popSlot();
            to.getLocalVars().setSlot(i, slot);
        }
    }

    public HeapObject getjThreadGroup() {
        return jThreadGroup;
    }

    public void setjThreadGroup(HeapObject jThreadGroup) {
        this.jThreadGroup = jThreadGroup;
    }

    public HeapObject getjThread() {
        return jThread;
    }

    public void setjThread(HeapObject jThread) {
        this.jThread = jThread;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
