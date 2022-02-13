package com.wjd.instructions.math;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * 无符号右移
 * @since 2021/12/5
 */
public class LUShr extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        long val1 = frame.getOperandStack().popLong();
        long val2 = frame.getOperandStack().popLong();
        long val = val2 >>> (val1 & 0x1f);
        frame.getOperandStack().pushLong(val);
    }
}
