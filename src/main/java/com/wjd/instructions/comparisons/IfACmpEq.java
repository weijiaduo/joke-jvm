package com.wjd.instructions.comparisons;

import com.wjd.instructions.base.BranchInstruction;
import com.wjd.rtda.Frame;

/**
 * Object 等于跳转
 * @since 2022/1/29
 */
public class IfACmpEq extends BranchInstruction {

    @Override
    public void execute(Frame frame) {
        Object v2 = frame.getOperandStack().popRef();
        Object v1 = frame.getOperandStack().popRef();
        if (v1 == v2) {
            branch(frame);
        }
    }

}
