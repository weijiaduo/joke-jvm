package com.wjd.naive.java.lang;

import com.wjd.naive.NativeClass;
import com.wjd.naive.NativeMethod;
import com.wjd.naive.NativeRegistry;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.StringPool;
import com.wjd.rtda.stack.Frame;

/**
 * @since 2022/2/13
 */
public class String implements NativeClass {

    static {
        NativeRegistry.registry("java/lang/String", "intern",
                "()Ljava/lang/String;", new Intern());
    }

    /**
     * public native String intern();
     */
    static class Intern implements NativeMethod {
        @Override
        public void execute(Frame frame) {
            HeapObject that = frame.getLocalVars().getThis();
            HeapObject internString = StringPool.internString(that);
            frame.getOperandStack().pushRef(internString);
        }
    }

}
