package com.wjd.jnative.java.io;

import com.wjd.jnative.NativeClass;
import com.wjd.jnative.NativeMethod;
import com.wjd.jnative.NativeRegistry;
import com.wjd.rtda.stack.Frame;

/**
 * @since 2022/2/15
 */
public class FileDescriptor implements NativeClass {

    static {
        NativeRegistry.registry("java/io/FileDescriptor", "initIDs",
                "()V", new InitIDs());
        NativeRegistry.registry("java/io/FileDescriptor", "set",
                "(I)J", new Set());
    }

    /**
     * private static native void initIDs();
     */
    static class InitIDs implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
        }
    }

    /**
     * private static native long set(int d);
     */
    static class Set implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            frame.getOpStack().pushLong(0);
        }
    }

}
