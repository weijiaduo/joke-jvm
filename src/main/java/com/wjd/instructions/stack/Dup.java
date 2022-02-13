package com.wjd.instructions.stack;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.Slot;

/**
 * @since 2021/12/5
 */
public class Dup extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Slot slot = frame.getOperandStack().popSlot();
        frame.getOperandStack().pushSlot(slot);
        frame.getOperandStack().pushSlot(slot);
    }
}
