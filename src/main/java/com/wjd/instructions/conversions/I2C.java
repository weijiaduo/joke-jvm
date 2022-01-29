package com.wjd.instructions.conversions;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.Frame;

/**
 * int 转 char
 * @since 2022/1/29
 */
public class I2C extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        int i = frame.getOperandStack().popInt();
        char c = (char) i;
        frame.getOperandStack().pushInt(c);
    }
}
