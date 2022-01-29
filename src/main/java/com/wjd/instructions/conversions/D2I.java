package com.wjd.instructions.conversions;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.Frame;

/**
 * double 转 int
 * @since 2022/1/29
 */
public class D2I extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        double d = frame.getOperandStack().popDouble();
        int i = (int) d;
        frame.getOperandStack().pushInt(i);
    }
}
