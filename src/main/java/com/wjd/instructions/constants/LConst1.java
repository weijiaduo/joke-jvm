package com.wjd.instructions.constants;

import com.wjd.rtda.stack.Frame;

/**
 * 把 long 1 推入栈顶
 *
 * @since 2021/12/1
 */
public class LConst1 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        frame.getOperandStack().pushLong(1L);
    }

}
