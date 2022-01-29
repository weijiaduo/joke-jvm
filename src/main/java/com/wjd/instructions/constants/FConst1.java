package com.wjd.instructions.constants;

import com.wjd.rtda.Frame;

/**
 * 把 float 1 推入栈顶
 *
 * @since 2021/12/1
 */
public class FConst1 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        frame.getOperandStack().pushFloat(1.0f);
    }

}
