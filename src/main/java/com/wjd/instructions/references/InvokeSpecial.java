package com.wjd.instructions.references;

import com.wjd.instructions.base.Index16Instruction;
import com.wjd.rtda.Frame;

/**
 * @since 2022/2/2
 */
public class InvokeSpecial extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        frame.getOperandStack().popRef();
    }
}
