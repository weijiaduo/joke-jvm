package com.wjd.instructions.conversions;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.Frame;

/**
 * float 转 double
 * @since 2022/1/29
 */
public class F2D extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        float f = frame.getOperandStack().popFloat();
        double d = f;
        frame.getOperandStack().pushDouble(d);
    }
}
