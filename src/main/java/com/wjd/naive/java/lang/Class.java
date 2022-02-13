package com.wjd.naive.java.lang;

import com.wjd.naive.NativeClass;
import com.wjd.naive.NativeMethod;
import com.wjd.naive.NativeRegistry;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.heap.ClassLoader;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.StringPool;

/**
 * @since 2022/2/12
 */
public class Class implements NativeClass {

    static {
        NativeRegistry.registry("java/lang/Class", "getPrimitiveClass",
                "(Ljava/lang/String;)Ljava/lang/Class;", new GetPrimitiveClass());
        NativeRegistry.registry("java/lang/Class", "getName0",
                "()Ljava/lang/String;", new GetName0());
        NativeRegistry.registry("java/lang/Class", "desiredAssertionStatus0",
                "(Ljava/lang/Class;)Z", new DesiredAssertionStatus0());
    }


    /**
     * static native Class<?> getPrimitiveClass(String name);
     */
    static class GetPrimitiveClass implements NativeMethod {
        @Override
        public void execute(Frame frame) {
            HeapObject nameObj = frame.getLocalVars().getRef(0);
            String name = StringPool.getString(nameObj);
            ClassLoader classLoader = frame.getMethod().getClazz().getLoader();
            HeapObject jClass = classLoader.loadClass(name).getjClass();
            frame.getOperandStack().pushRef(jClass);
        }
    }

    /**
     * private native String getName0();
     */
    static class GetName0 implements NativeMethod {
        @Override
        public void execute(Frame frame) {
            HeapObject that = frame.getLocalVars().getThis();
            ClassMeta classMeta = (ClassMeta) that.getExtra();
            String name = classMeta.getJavaName();
            HeapObject nameObj = StringPool.getJString(classMeta.getLoader(), name);
            frame.getOperandStack().pushRef(nameObj);
        }
    }

    static class DesiredAssertionStatus0 implements NativeMethod {
        @Override
        public void execute(Frame frame) {
            frame.getOperandStack().pushBoolean(false);
        }
    }

}
