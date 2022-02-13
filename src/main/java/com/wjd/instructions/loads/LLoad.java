package com.wjd.instructions.loads;

import com.wjd.instructions.base.Index8Instruction;
import com.wjd.rtda.stack.Frame;

/**
 * 把指定位置的本地变量推入栈顶
 *
 * @since 2021/12/1
 */
public class LLoad extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        LoadUtil.lload(frame, index);
    }

}
