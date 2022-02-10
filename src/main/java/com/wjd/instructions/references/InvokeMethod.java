package com.wjd.instructions.references;

import com.wjd.instructions.base.Index16Instruction;
import com.wjd.rtda.Frame;
import com.wjd.rtda.Slot;
import com.wjd.rtda.Thread;
import com.wjd.rtda.heap.member.Method;

/**
 * @since 2022/2/4
 */
public class InvokeMethod extends Index16Instruction {

    protected void invokeMethod(Frame frame, Method method) {
        Thread thread = frame.getThread();
        Frame newFrame = thread.newFrame(method);
        thread.pushFrame(newFrame);
        int argsSlotCount = method.getArgSlotCount();
        if (argsSlotCount > 0) {
            for (int i = argsSlotCount - 1; i >= 0; i--) {
                Slot slot = frame.getOperandStack().popSlot();
                newFrame.getLocalVars().setSlot(i, slot);
            }
        }

        // 临时处理本地方法
        if (method.isNative()) {
            if ("registerNatives".equals(method.getName())) {
                thread.popFrame();
            } else {
                throw new IllegalAccessError("native method: " + method.getName());
            }
        }
    }

}
