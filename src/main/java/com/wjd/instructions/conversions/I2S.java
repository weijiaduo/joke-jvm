package com.wjd.instructions.conversions;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.Frame;

/**
 * int 转 short
 * @since 2022/1/29
 */
public class I2S extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        int i = frame.getOperandStack().popInt();
        short s = (short) i;
        frame.getOperandStack().pushInt(s);
    }
}
