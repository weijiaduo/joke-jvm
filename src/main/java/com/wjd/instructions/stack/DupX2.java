package com.wjd.instructions.stack;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.Frame;
import com.wjd.rtda.Slot;

/**
 * @since 2021/12/5
 */
public class DupX2 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Slot slot1 = frame.getOperandStack().popSlot();
        Slot slot2 = frame.getOperandStack().popSlot();
        Slot slot3 = frame.getOperandStack().popSlot();
        frame.getOperandStack().pushSlot(slot1);
        frame.getOperandStack().pushSlot(slot3);
        frame.getOperandStack().pushSlot(slot2);
        frame.getOperandStack().pushSlot(slot1);
    }
}
