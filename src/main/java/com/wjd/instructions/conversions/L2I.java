package com.wjd.instructions.conversions;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * long 转 int
 * @since 2022/1/29
 */
public class L2I extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        long l = frame.getOperandStack().popLong();
        int i = (int) l;
        frame.getOperandStack().pushInt(i);
    }
}
