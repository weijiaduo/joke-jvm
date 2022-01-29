package com.wjd.instructions.comparisons;

import com.wjd.instructions.base.BranchInstruction;
import com.wjd.rtda.Frame;

/**
 * int 小于等于跳转
 * @since 2022/1/29
 */
public class IfICmpLe extends BranchInstruction {

    @Override
    public void execute(Frame frame) {
        int v2 = frame.getOperandStack().popInt();
        int v1 = frame.getOperandStack().popInt();
        if (v1 <= v2) {
            branch(frame);
        }
    }

}
