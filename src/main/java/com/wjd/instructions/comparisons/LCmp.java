package com.wjd.instructions.comparisons;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * long 比较
 * @since 2022/1/29
 */
public class LCmp extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        long v2 = frame.getOpStack().popLong();
        long v1 = frame.getOpStack().popLong();
        int v = CmpUtil.cmpLong(v1, v2);
        frame.getOpStack().pushInt(v);
    }
}
