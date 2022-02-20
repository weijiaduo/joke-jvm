package com.wjd.instructions.conversions;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * long 转 double
 * @since 2022/1/29
 */
public class L2D extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        long l = frame.getOpStack().popLong();
        double d = (double) l;
        frame.getOpStack().pushDouble(d);
    }
}
