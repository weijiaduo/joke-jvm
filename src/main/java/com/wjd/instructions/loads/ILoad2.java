package com.wjd.instructions.loads;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.Frame;

/**
 * 把第2个局部变量推入栈顶
 *
 * @since 2021/12/1
 */
public class ILoad2 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        LoadUtil.iload(frame, 2);
    }

}
