package com.wjd.naive.java.lang;

import com.wjd.naive.NativeClass;
import com.wjd.naive.NativeMethod;
import com.wjd.naive.NativeRegistry;
import com.wjd.rtda.stack.Frame;

/**
 * @since 2022/2/13
 */
public class Double implements NativeClass {

    static {
        NativeRegistry.registry("java/lang/Double", "doubleToRawLongBits",
                "(D)J", new DoubleToRawLongBits());
        NativeRegistry.registry("java/lang/Double", "longBitsToDouble",
                "(J)D", new LongBitsToDouble());
    }

    /**
     * public static native long doubleToRawLongBits(double value);
     */
    static class DoubleToRawLongBits implements NativeMethod {
        @Override
        public void execute(Frame frame) {
            double val = frame.getLocalVars().getDouble(0);
            long bits = java.lang.Double.doubleToRawLongBits(val);
            frame.getOpStack().pushLong(bits);
        }
    }

    /**
     * public static native double longBitsToDouble(long bits);
     */
    static class LongBitsToDouble implements NativeMethod {
        @Override
        public void execute(Frame frame) {
            long bits = frame.getLocalVars().getLong(0);
            double val = java.lang.Double.longBitsToDouble(bits);
            frame.getOpStack().pushDouble(val);
        }
    }

}
