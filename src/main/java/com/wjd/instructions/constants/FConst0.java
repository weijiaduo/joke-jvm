package com.wjd.instructions.constants;

import com.wjd.rtda.Frame;

/**
 * 把 float 0 推入栈顶
 *
 * @since 2021/12/1
 */
public class FConst0 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        frame.getOperandStack().pushFloat(0.0f);
    }

}
