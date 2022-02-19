package com.wjd.instructions.conversions;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * long 转 float
 * @since 2022/1/29
 */
public class L2F extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        long l = frame.getOpStack().popLong();
        float f = (float) l;
        frame.getOpStack().pushFloat(f);
    }
}
