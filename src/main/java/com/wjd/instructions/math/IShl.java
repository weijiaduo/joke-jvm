package com.wjd.instructions.math;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.Frame;

/**
 * 左移
 * @since 2021/12/5
 */
public class IShl extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        int val1 = frame.getOperandStack().popInt();
        int val2 = frame.getOperandStack().popInt();
        int val = val2 << (val1 & 0x1f);
        frame.getOperandStack().pushInt(val);
    }
}
