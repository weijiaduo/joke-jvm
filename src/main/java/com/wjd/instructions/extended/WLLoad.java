package com.wjd.instructions.extended;

import com.wjd.instructions.base.Index16Instruction;
import com.wjd.instructions.loads.LoadUtil;
import com.wjd.rtda.stack.Frame;

/**
 * 把指定位置的本地变量推入栈顶
 *
 * @since 2021/12/1
 */
public class WLLoad extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        LoadUtil.lload(frame, index);
    }

}
