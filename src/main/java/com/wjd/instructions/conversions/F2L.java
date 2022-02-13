package com.wjd.instructions.conversions;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * float 转 long
 * @since 2022/1/29
 */
public class F2L extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        float f = frame.getOperandStack().popFloat();
        long l = (int) f;
        frame.getOperandStack().pushLong(l);
    }
}
