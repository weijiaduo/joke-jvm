package com.wjd.instructions.math;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.Frame;

/**
 * 减法
 * @since 2021/12/5
 */
public class DSub extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        double val1 = frame.getOperandStack().popDouble();
        double val2 = frame.getOperandStack().popDouble();
        double val = val2 - val1;
        frame.getOperandStack().pushDouble(val);
    }
}
