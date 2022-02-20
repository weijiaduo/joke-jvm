package com.wjd.instructions.constants;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * 把 float 2 推入栈顶
 *
 * @since 2021/12/1
 */
public class FConst2 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        frame.getOpStack().pushFloat(2.0f);
    }

}
