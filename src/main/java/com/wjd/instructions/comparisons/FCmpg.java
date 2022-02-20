package com.wjd.instructions.comparisons;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * float 比较
 * @since 2022/1/29
 */
public class FCmpg extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        float v2 = frame.getOpStack().popFloat();
        float v1 = frame.getOpStack().popFloat();
        int v = CmpUtil.cmpFloat(v1, v2, true);
        frame.getOpStack().pushInt(v);
    }
}
