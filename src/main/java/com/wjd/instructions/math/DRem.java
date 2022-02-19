package com.wjd.instructions.math;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * 取余
 * @since 2021/12/5
 */
public class DRem extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        double val1 = frame.getOpStack().popDouble();
        double val2 = frame.getOpStack().popDouble();
        double val = val2 % val1;
        frame.getOpStack().pushDouble(val);
    }
}
