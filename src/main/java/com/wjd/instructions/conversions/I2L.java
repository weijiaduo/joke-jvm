package com.wjd.instructions.conversions;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.Frame;

/**
 * int 转 long
 * @since 2022/1/29
 */
public class I2L extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        int i = frame.getOperandStack().popInt();
        long l = i;
        frame.getOperandStack().pushLong(l);
    }
}
