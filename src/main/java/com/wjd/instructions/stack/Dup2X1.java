package com.wjd.instructions.stack;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.Slot;

/**
 * @since 2021/12/5
 */
public class Dup2X1 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Slot slot1 = frame.getOpStack().popSlot();
        Slot slot2 = frame.getOpStack().popSlot();
        Slot slot3 = frame.getOpStack().popSlot();
        frame.getOpStack().pushSlot(slot2);
        frame.getOpStack().pushSlot(slot1);
        frame.getOpStack().pushSlot(slot3);
        frame.getOpStack().pushSlot(slot2);
        frame.getOpStack().pushSlot(slot1);
    }
}
