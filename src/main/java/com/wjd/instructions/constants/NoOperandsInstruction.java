package com.wjd.instructions.constants;

import com.wjd.instructions.base.ByteCodeReader;
import com.wjd.instructions.base.Instruction;
import com.wjd.rtda.Frame;

/**
 * @since 2021/11/6
 */
public class NoOperandsInstruction implements Instruction {

    @Override
    public void execute(Frame frame) {
        // 什么也不做
    }

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        // 什么也不做
    }

}
