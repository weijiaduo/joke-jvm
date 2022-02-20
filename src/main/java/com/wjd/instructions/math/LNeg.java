package com.wjd.instructions.math;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * 取反
 * @since 2021/12/5
 */
public class LNeg extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        long val = -frame.getOpStack().popLong();
        frame.getOpStack().pushLong(val);
    }
}
