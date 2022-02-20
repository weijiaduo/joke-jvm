package com.wjd.instructions.references;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.Thread;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.ex.StackTraceElement;
import com.wjd.rtda.meta.StringPool;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.stack.OperandStack;

/**
 * 抛出异常
 * @since 2022/2/14
 */
public class AThrow extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        HeapObject ex = frame.getOpStack().popRef();
        if (ex == null) {
            throw new NullPointerException("AThrow exception is null!");
        }
        Thread thread = frame.getThread();
        if (!findAndGotoExceptionHandler(thread, ex)) {
            handleUncaughtException(thread, ex);
        }
    }

    /**
     * 寻找并捕获异常
     */
    private boolean findAndGotoExceptionHandler(Thread thread, HeapObject ex) {
        do {
            Frame frame = thread.currentFrame();
            int pc = frame.getNextPc() - 1;
            int handlerPc = frame.getMethod().findExceptionHandler(ex.getClazz(), pc);
            if (handlerPc > 0) {
                OperandStack stack = frame.getOpStack();
                stack.clear();
                stack.pushRef(ex);
                frame.setNextPc(handlerPc);
                return true;
            }
            thread.popFrame();
        } while (!thread.isStackEmpty());
        return false;
    }

    /**
     * 没有捕获到异常的情况
     */
    private void handleUncaughtException(Thread thread, HeapObject ex) {
        thread.clearStack();
        HeapObject msgObj = ex.getFieldRef("detailMessage", "Ljava/lang/String;");
        String msg = StringPool.getRawString(msgObj);
        System.out.println("Exception in " + ex.getClazz().getJavaName() + ": " + msg);
        Object extra = ex.getExtra();
        if (extra instanceof StackTraceElement[]) {
            StackTraceElement[] stacks = (StackTraceElement[]) extra;
            for (StackTraceElement stack : stacks) {
                System.out.println("\tat " + stack);
            }
        } else {
            System.out.println("detail: " + extra);
        }
    }

}
