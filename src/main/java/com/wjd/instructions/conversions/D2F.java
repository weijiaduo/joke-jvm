package com.wjd.instructions.conversions;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * double 转 float
 * @since 2022/1/29
 */
public class D2F extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        double d = frame.getOperandStack().popDouble();
        float f = (float) d;
        frame.getOperandStack().pushFloat(f);
    }
}
