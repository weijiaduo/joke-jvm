package com.wjd.instructions.base;

import com.wjd.rtda.stack.Frame;

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
