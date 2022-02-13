package com.wjd.instructions.math;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * 取反
 * @since 2021/12/5
 */
public class INeg extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        int val = -frame.getOperandStack().popInt();
        frame.getOperandStack().pushInt(val);
    }
}
