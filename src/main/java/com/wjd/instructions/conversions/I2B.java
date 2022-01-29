package com.wjd.instructions.conversions;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.Frame;

/**
 * int 转 byte
 * @since 2022/1/29
 */
public class I2B extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        int i = frame.getOperandStack().popInt();
        byte b = (byte) i;
        frame.getOperandStack().pushInt(b);
    }
}
