package com.wjd.naive.sun.io;

import com.wjd.naive.NativeClass;
import com.wjd.naive.NativeMethod;
import com.wjd.naive.NativeRegistry;
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
            frame.getOperandStack().pushLong(0);
        }
    }

}
