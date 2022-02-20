package com.wjd.instructions.conversions;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * int 转 short
 * @since 2022/1/29
 */
public class I2S extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        int i = frame.getOpStack().popInt();
        short s = (short) i;
        frame.getOpStack().pushInt(s);
    }
}
