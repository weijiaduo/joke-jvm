package com.wjd.instructions.math;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * 取反
 * @since 2021/12/5
 */
public class FNeg extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        float val = -frame.getOpStack().popFloat();
        frame.getOpStack().pushFloat(val);
    }
}
