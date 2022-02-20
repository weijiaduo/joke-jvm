package com.wjd.instructions.conversions;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * long 转 int
 * @since 2022/1/29
 */
public class L2I extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        long l = frame.getOpStack().popLong();
        int i = (int) l;
        frame.getOpStack().pushInt(i);
    }
}
