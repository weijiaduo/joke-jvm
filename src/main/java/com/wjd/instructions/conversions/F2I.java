package com.wjd.instructions.conversions;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * float 转 int
 * @since 2022/1/29
 */
public class F2I extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        float f = frame.getOperandStack().popFloat();
        int i = (int) f;
        frame.getOperandStack().pushInt(i);
    }
}
