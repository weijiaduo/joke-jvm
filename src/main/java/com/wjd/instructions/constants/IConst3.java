package com.wjd.instructions.constants;

import com.wjd.rtda.Frame;

/**
 * 把 int 3 推入栈顶
 *
 * @since 2021/12/1
 */
public class IConst3 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        frame.getOperandStack().pushInt(3);
    }

}
