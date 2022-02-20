package com.wjd.instructions.conversions;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * float 转 long
 * @since 2022/1/29
 */
public class F2L extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        float f = frame.getOpStack().popFloat();
        long l = (int) f;
        frame.getOpStack().pushLong(l);
    }
}
