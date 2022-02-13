package com.wjd.instructions.conversions;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * int 转 double
 * @since 2022/1/29
 */
public class I2D extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        double d = frame.getOperandStack().popInt();
        frame.getOperandStack().pushDouble(d);
    }
}
