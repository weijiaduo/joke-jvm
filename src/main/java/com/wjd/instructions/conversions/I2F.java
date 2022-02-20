package com.wjd.instructions.conversions;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * int 转 float
 * @since 2022/1/29
 */
public class I2F extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        float f = frame.getOpStack().popInt();
        frame.getOpStack().pushFloat(f);
    }
}
