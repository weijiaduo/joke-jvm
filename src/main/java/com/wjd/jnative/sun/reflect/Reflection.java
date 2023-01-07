package com.wjd.jnative.sun.reflect;

import com.wjd.jnative.NativeClass;
import com.wjd.jnative.NativeMethod;
import com.wjd.jnative.NativeRegistry;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.stack.Frame;

/**
 * @since 2022/2/15
 */
public class Reflection implements NativeClass {

    static {
        NativeRegistry.registry("sun/reflect/Reflection", "getCallerClass",
                "()Ljava/lang/Class;", new GetCallerClass());
        NativeRegistry.registry("sun/reflect/Reflection", "getClassAccessFlags",
                "(Ljava/lang/Class;)I", new GetClassAccessFlags());
    }

    /**
     * public static native Class<?> getCallerClass(int i);
     */
    static class GetCallerClass implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            // top0 is sun/reflect/Reflection
            // top1 is the caller of getCallerClass()
            // top2 is the caller of method
            Frame callerFrame = frame.getThread().getTopFrameN(2);
            HeapObject callerClass = callerFrame.getMethod().getClassMeta().getjClass();
            frame.getOpStack().pushRef(callerClass);
        }
    }

    /**
     * public static native int getClassAccessFlags(Class<?> type);
     */
    static class GetClassAccessFlags implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            HeapObject type = frame.getLocalVars().getRef(0);
            ClassMeta classMeta = (ClassMeta) type.getExtra();
            frame.getOpStack().pushInt(classMeta.getAccessFlags().value());
        }
    }

}
