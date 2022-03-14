package com.wjd.jnative.sun.io;

import com.wjd.jnative.NativeClass;
import com.wjd.jnative.NativeMethod;
import com.wjd.jnative.NativeRegistry;
import com.wjd.rtda.stack.Frame;

/**
 * @since 2022/2/18
 */
public class Win32ErrorMode implements NativeClass {

    static {
        NativeRegistry.registry("sun/io/Win32ErrorMode", "setErrorMode",
                "(J)J", new SetErrorMode());
    }

    /**
     * private static native long setErrorMode(long var0);
     */
    static class SetErrorMode implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            frame.getOpStack().pushLong(0);
        }
    }

}
