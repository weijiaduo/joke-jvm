package com.wjd.instructions.loads;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * 把第0个局部变量推入栈顶
 *
 * @since 2021/12/1
 */
public class FLoad1 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        LoadUtil.fload(frame, 1);
    }

}
