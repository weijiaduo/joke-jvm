package com.wjd.instructions.constants;

import com.wjd.rtda.stack.Frame;

/**
 * 把 double 0 推入栈顶
 *
 * @since 2021/12/1
 */
public class DConst0 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        frame.getOperandStack().pushDouble(0.0);
    }

}
