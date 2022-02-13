package com.wjd.instructions.math;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * 与
 * @since 2021/12/5
 */
public class IAnd extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        int val1 = frame.getOperandStack().popInt();
        int val2 = frame.getOperandStack().popInt();
        int val = val2 & val1;
        frame.getOperandStack().pushInt(val);
    }
}
