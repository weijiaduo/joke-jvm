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
        NativeRegistry.registry("java/lang/Class", "getClassLoader0",
                "()Ljava/lang/ClassLoader;", new GetClassLoader0());
    }


    /**
     * static native Class<?> getPrimitiveClass(String name);
     */
    static class GetPrimitiveClass implements NativeMethod {
        @Override
        public void execute(Frame frame) {
            HeapObject nameObj = frame.getLocalVars().getRef(0);
            java.lang.String name = StringPool.getRawString(nameObj);
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
            java.lang.String name = classMeta.getJavaName();
            HeapObject nameObj = StringPool.getObjString(classMeta.getLoader(), name);
            frame.getOperandStack().pushRef(nameObj);
        }
    }

    /**
     * private static native boolean desiredAssertionStatus0(Class<?> clazz);
     */
    static class DesiredAssertionStatus0 implements NativeMethod {
        @Override
        public void execute(Frame frame) {
            frame.getOperandStack().pushBoolean(false);
        }
    }

    /**
     * native ClassLoader getClassLoader0();
     */
    static class GetClassLoader0 implements NativeMethod {
        @Override
        public void execute(Frame frame) {
            frame.getOperandStack().pushRef(null);
        }
    }

}
