package com.wjd.instructions.stack;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * @since 2021/12/5
 */
public class Pop extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        frame.getOpStack().popSlot();
    }
}
