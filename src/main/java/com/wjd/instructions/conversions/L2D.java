package com.wjd.instructions.conversions;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * long 转 double
 * @since 2022/1/29
 */
public class L2D extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        long l = frame.getOperandStack().popLong();
        double d = (float) l;
        frame.getOperandStack().pushDouble(d);
    }
}
