package com.wjd.instructions.conversions;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.Frame;

/**
 * double 转 long
 * @since 2022/1/29
 */
public class D2L extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        double d = frame.getOperandStack().popDouble();
        long l = (long) d;
        frame.getOperandStack().pushLong(l);
    }
}
