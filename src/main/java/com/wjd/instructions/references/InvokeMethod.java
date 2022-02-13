package com.wjd.instructions.references;

import com.wjd.instructions.base.Index16Instruction;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.Slot;
import com.wjd.rtda.Thread;
import com.wjd.rtda.meta.MethodMeta;

/**
 * @since 2022/2/4
 */
public class InvokeMethod extends Index16Instruction {

    protected void invokeMethod(Frame frame, MethodMeta methodMeta) {
        Thread thread = frame.getThread();
        Frame newFrame = thread.newFrame(methodMeta);
        thread.pushFrame(newFrame);
        int argsSlotCount = methodMeta.getArgSlotCount();
        if (argsSlotCount > 0) {
            for (int i = argsSlotCount - 1; i >= 0; i--) {
                Slot slot = frame.getOperandStack().popSlot();
                newFrame.getLocalVars().setSlot(i, slot);
            }
        }
    }

}
