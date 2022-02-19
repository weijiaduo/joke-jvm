package com.wjd.instructions.math;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * 取反
 * @since 2021/12/5
 */
public class DNeg extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        double val = -frame.getOpStack().popDouble();
        frame.getOpStack().pushDouble(val);
    }
}
