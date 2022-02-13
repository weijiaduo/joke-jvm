package com.wjd.naive;

import com.wjd.rtda.stack.Frame;

/**
 * 空的本地方法
 * @since 2022/2/12
 */
public class EmptyNativeMethod implements NativeMethod {

    public void execute(Frame frame) {
        // 不做任何事
    }

}
