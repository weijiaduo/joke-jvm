package com.wjd.instructions.math;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.Frame;

/**
 * 取反
 * @since 2021/12/5
 */
public class LNeg extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        long val = -frame.getOperandStack().popLong();
        frame.getOperandStack().pushLong(val);
    }
}
