package com.wjd.instructions.math;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * 除法
 * @since 2021/12/5
 */
public class IDiv extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        int val1 = frame.getOpStack().popInt();
        int val2 = frame.getOpStack().popInt();
        int val = val2 / val1;
        frame.getOpStack().pushInt(val);
    }
}
