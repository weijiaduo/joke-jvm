package com.wjd.instructions.math;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.Frame;

/**
 * 右移
 * @since 2021/12/5
 */
public class LShr extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        long val1 = frame.getOperandStack().popLong();
        long val2 = frame.getOperandStack().popLong();
        long val = val2 >> (val1 & 0x1f);
        frame.getOperandStack().pushLong(val);
    }
}
