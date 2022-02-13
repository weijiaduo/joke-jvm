package com.wjd.instructions.control;

import com.wjd.instructions.base.BranchInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * goto 跳转
 * @since 2022/1/29
 */
public class Goto extends BranchInstruction {

    @Override
    public void execute(Frame frame) {
        branch(frame);
    }
}
