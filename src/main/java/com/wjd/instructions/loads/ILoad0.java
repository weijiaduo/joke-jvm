package com.wjd.instructions.loads;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.Frame;

/**
 * 把第0个局部变量推入栈顶
 *
 * @since 2021/12/1
 */
public class ILoad0 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        LoadUtil.iload(frame, 0);
    }

}
