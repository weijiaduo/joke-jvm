package com.wjd.instructions.conversions;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.Frame;

/**
 * int 转 double
 * @since 2022/1/29
 */
public class I2D extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        int i = frame.getOperandStack().popInt();
        double d = i;
        frame.getOperandStack().pushDouble(d);
    }
}
