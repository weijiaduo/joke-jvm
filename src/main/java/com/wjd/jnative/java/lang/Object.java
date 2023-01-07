package com.wjd.jnative.java.lang;

import com.wjd.jnative.NativeClass;
import com.wjd.jnative.NativeMethod;
import com.wjd.jnative.NativeRegistry;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.heap.HeapObject;

/**
 * @since 2022/2/12
 */
public class Object implements NativeClass {

    static {
        NativeRegistry.registry("java/lang/Object", "getClass",
                "()Ljava/lang/Class;", new GetClass());
        NativeRegistry.registry("java/lang/Object", "hashCode",
                "()I", new HashCode());
        NativeRegistry.registry("java/lang/Object", "clone",
                "()Ljava/lang/Object;", new Clone());
        NativeRegistry.registry("java/lang/Object", "wait",
                "(J)V", new Wait());
        NativeRegistry.registry("java/lang/Object", "notifyAll",
                "()V", new NotifyAll());
    }

    /**
     * public final native Class<?> getClass();
     */
    static class GetClass implements NativeMethod {
        @Override
        public void execute(Frame frame) {
            HeapObject that = frame.getLocalVars().getThis();
            HeapObject jClassObj = that.getClassMeta().getjClass();
            frame.getOpStack().pushRef(jClassObj);
        }
    }

    /**
     * public native int hashCode();
     */
    static class HashCode implements NativeMethod {
        @Override
        public void execute(Frame frame) {
            HeapObject that = frame.getLocalVars().getThis();
            int hashcode = that.hashCode();
            frame.getOpStack().pushInt(hashcode);
        }
    }

    /**
     * protected native Object clone() throws CloneNotSupportedException;
     */
    static class Clone implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            HeapObject that = frame.getLocalVars().getThis();

            // 必须要实现java/lang/Cloneable才行
            ClassMeta cloneableClassMeta = that.getClassMeta().getLoader().loadClass("java/lang/Cloneable");
            if (!that.getClassMeta().isImplements(cloneableClassMeta)) {
                throw new CloneNotSupportedException("Not support clone: " + that.getClassMeta().getName());
            }

            // 克隆对象
            frame.getOpStack().pushRef(that.clone());
        }
    }

    /**
     * public final native void wait(long timeout) throws InterruptedException;
     */
    static class Wait implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
        }
    }

    /**
     * public final native void notifyAll();
     */
    static class NotifyAll implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
        }
    }

}
