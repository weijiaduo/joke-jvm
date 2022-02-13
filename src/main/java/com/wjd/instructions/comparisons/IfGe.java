package com.wjd.instructions.comparisons;

import com.wjd.instructions.base.BranchInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * int 大于等于 0 跳转
 * @since 2022/1/29
 */
public class IfGe extends BranchInstruction {

    @Override
    public void execute(Frame frame) {
        int v1 = frame.getOperandStack().popInt();
        if (v1 >= 0) {
            branch(frame);
        }
    }

}
