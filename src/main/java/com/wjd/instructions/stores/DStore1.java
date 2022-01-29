package com.wjd.instructions.stores;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.Frame;

/**
 * 把指定位置的本地变量推入栈顶
 *
 * @since 2021/12/1
 */
public class DStore1 extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        StoreUtil.dstore(frame, 1);
    }

}
