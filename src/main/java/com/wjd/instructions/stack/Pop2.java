package com.wjd.instructions.stack;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * @since 2021/12/5
 */
public class Pop2 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        frame.getOpStack().popSlot();
        frame.getOpStack().popSlot();
    }
}
