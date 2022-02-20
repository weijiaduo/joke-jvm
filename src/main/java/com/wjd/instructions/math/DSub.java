package com.wjd.instructions.math;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * 减法
 * @since 2021/12/5
 */
public class DSub extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        double val1 = frame.getOpStack().popDouble();
        double val2 = frame.getOpStack().popDouble();
        double val = val2 - val1;
        frame.getOpStack().pushDouble(val);
    }
}
