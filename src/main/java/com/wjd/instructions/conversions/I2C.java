package com.wjd.instructions.conversions;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * int 转 char
 * @since 2022/1/29
 */
public class I2C extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        int i = frame.getOpStack().popInt();
        char c = (char) i;
        frame.getOpStack().pushInt(c);
    }
}
