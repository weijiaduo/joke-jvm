package com.wjd.instructions.math;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * 除法
 * @since 2021/12/5
 */
public class LDiv extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        long val1 = frame.getOpStack().popLong();
        long val2 = frame.getOpStack().popLong();
        long val = val2 / val1;
        frame.getOpStack().pushLong(val);
    }
}
