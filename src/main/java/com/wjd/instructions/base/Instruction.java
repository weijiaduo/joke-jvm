package com.wjd.instructions.base;

import com.wjd.rtda.stack.Frame;

/**
 * @since 2021/11/6
 */
public interface Instruction {

    void execute(Frame frame);

    void fetchOperands(ByteCodeReader reader);

}
