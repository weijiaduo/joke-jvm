package com.wjd.instructions.math;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.Frame;

/**
 * 乘法
 * @since 2021/12/5
 */
public class FMul extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        float val1 = frame.getOperandStack().popFloat();
        float val2 = frame.getOperandStack().popFloat();
        float val = val2 * val1;
        frame.getOperandStack().pushFloat(val);
    }
}
