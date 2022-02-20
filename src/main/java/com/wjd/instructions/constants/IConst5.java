package com.wjd.instructions.constants;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * 把 int 5 推入栈顶
 *
 * @since 2021/12/1
 */
public class IConst5 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        frame.getOpStack().pushInt(5);
    }

}
