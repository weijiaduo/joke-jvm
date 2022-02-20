package com.wjd.instructions.comparisons;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * double 比较
 * @since 2022/1/29
 */
public class DCmpl extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        double v2 = frame.getOpStack().popDouble();
        double v1 = frame.getOpStack().popDouble();
        int v = CmpUtil.cmpDouble(v1, v2, false);
        frame.getOpStack().pushInt(v);
    }
}
