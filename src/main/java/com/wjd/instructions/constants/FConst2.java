package com.wjd.instructions.constants;

import com.wjd.rtda.Frame;

/**
 * 把 float 2 推入栈顶
 *
 * @since 2021/12/1
 */
public class FConst2 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        frame.getOperandStack().pushFloat(2.0f);
    }

}
