package com.wjd.instructions.math;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.Frame;

/**
 * 取反
 * @since 2021/12/5
 */
public class FNeg extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        float val = -frame.getOperandStack().popFloat();
        frame.getOperandStack().pushFloat(val);
    }
}
