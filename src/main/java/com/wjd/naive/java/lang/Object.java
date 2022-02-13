package com.wjd.naive.java.lang;

import com.wjd.naive.NativeClass;
import com.wjd.naive.NativeMethod;
import com.wjd.naive.NativeRegistry;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.heap.HeapObject;

/**
 * @since 2022/2/12
 */
public class Object implements NativeClass {

    static {
        NativeRegistry.registry("java/lang/Object", "getClass",
                "()Ljava/lang/Class;", new GetClass());
    }

    /**
     * public final native Class<?> getClass();
     */
    static class GetClass implements NativeMethod {
        @Override
        public void execute(Frame frame) {
            HeapObject that = frame.getLocalVars().getThis();
            HeapObject jClassObj = that.getClazz().getjClass();
            frame.getOperandStack().pushRef(jClassObj);
        }
    }

}
