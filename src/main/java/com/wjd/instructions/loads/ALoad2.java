package com.wjd.instructions.loads;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.Frame;

/**
 * 把指定位置的本地变量推入栈顶
 *
 * @since 2021/12/1
 */
public class ALoad2 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        LoadUtil.aload(frame, 2);
    }

}
