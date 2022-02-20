package com.wjd.instructions.comparisons;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * int 比较
 * @since 2022/1/29
 */
public class ICmp extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        int v2 = frame.getOpStack().popInt();
        int v1 = frame.getOpStack().popInt();
        int v = CmpUtil.cmpInt(v1, v2);
        frame.getOpStack().pushInt(v);
    }
}
