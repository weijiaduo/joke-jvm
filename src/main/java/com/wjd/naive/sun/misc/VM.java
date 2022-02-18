package com.wjd.naive.sun.misc;

import com.wjd.naive.NativeClass;
import com.wjd.naive.NativeMethod;
import com.wjd.naive.NativeRegistry;
import com.wjd.rtda.stack.Frame;

/**
 * @since 2022/2/13
 */
public class VM implements NativeClass {

    static {
        NativeRegistry.registry("sun/misc/VM", "initialize",
                "()V", new Initialize());
    }

    /**
     * TODO: 暂时给VM的savedProps设置一个值，foo=bar
     * private static native void initialize();
     */
    static class Initialize implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
        }
    }

}
