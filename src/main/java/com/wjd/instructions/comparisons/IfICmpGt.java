package com.wjd.instructions.comparisons;

import com.wjd.instructions.base.BranchInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * int 大于跳转
 * @since 2022/1/29
 */
public class IfICmpGt extends BranchInstruction {

    @Override
    public void execute(Frame frame) {
        int v2 = frame.getOpStack().popInt();
        int v1 = frame.getOpStack().popInt();
        if (v1 > v2) {
            branch(frame);
        }
    }

}
