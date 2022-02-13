package com.wjd.naive;

import com.wjd.rtda.stack.Frame;

/**
 * 本地方法接口
 * @since 2022/2/12
 */
public interface NativeMethod {

    void execute(Frame frame) throws Exception;

}
