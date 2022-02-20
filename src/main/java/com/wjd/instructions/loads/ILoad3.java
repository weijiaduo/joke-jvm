package com.wjd.instructions.loads;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;

/**
 * 把第3个局部变量推入栈顶
 *
 * @since 2021/12/1
 */
public class ILoad3 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        LoadUtil.iload(frame, 3);
    }

}
