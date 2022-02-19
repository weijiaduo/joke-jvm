package com.wjd.instructions.comparisons;

import com.wjd.instructions.base.BranchInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * Object 不等于跳转
 * @since 2022/1/29
 */
public class IfACmpNe extends BranchInstruction {

    @Override
    public void execute(Frame frame) {
        Object v2 = frame.getOpStack().popRef();
        Object v1 = frame.getOpStack().popRef();
        if (v1 != v2) {
            branch(frame);
        }
    }

}
