package com.wjd.naive.sun.misc;

import com.wjd.naive.NativeClass;
import com.wjd.naive.NativeMethod;
import com.wjd.naive.NativeRegistry;
import com.wjd.rtda.stack.Frame;

/**
 * @since 2022/2/18
 */
public class Signal implements NativeClass {

    static {
        NativeRegistry.registry("sun/misc/Signal", "findSignal",
                "(Ljava/lang/String;)I", new FindSignal());
        NativeRegistry.registry("sun/misc/Signal", "handle0",
                "(IJ)J", new Handle0());
    }

    /**
     * private static native int findSignal(String sigName);
     */
    static class FindSignal implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            frame.getOperandStack().pushInt(0);
        }
    }

    /**
     * private static native long handle0(int sig, long nativeH);
     */
    static class Handle0 implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            frame.getOperandStack().pushLong(0);
        }
    }

}
