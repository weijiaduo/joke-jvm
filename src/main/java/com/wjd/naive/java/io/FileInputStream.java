package com.wjd.naive.java.io;

import com.wjd.naive.NativeClass;
import com.wjd.naive.NativeMethod;
import com.wjd.naive.NativeRegistry;
import com.wjd.rtda.stack.Frame;

/**
 * @since 2022/2/15
 */
public class FileInputStream implements NativeClass {

    static {
        NativeRegistry.registry("java/io/FileInputStream", "initIDs",
                "()V", new InitIDs());
    }

    /**
     * private static native void initIDs();
     */
    static class InitIDs implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
        }
    }

}
