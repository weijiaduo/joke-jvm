package com.wjd.instructions.math;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.Frame;

/**
 * 或
 * @since 2021/12/5
 */
public class LOr extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        long val1 = frame.getOperandStack().popLong();
        long val2 = frame.getOperandStack().popLong();
        long val = val2 | val1;
        frame.getOperandStack().pushLong(val);
    }
}
