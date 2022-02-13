package com.wjd.naive.java.lang;

import com.wjd.naive.NativeClass;
import com.wjd.naive.NativeMethod;
import com.wjd.naive.NativeRegistry;
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

    /**
     * public native int hashCode();
     */
    static class HashCode implements NativeMethod {
        @Override
        public void execute(Frame frame) {
            HeapObject that = frame.getLocalVars().getThis();
            int hashcode = that.hashCode();
            frame.getOperandStack().pushInt(hashcode);
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
            ClassMeta cloneableClassMeta = that.getClazz().getLoader().loadClass("java/lang/Cloneable");
            if (!that.getClazz().isImplements(cloneableClassMeta)) {
                throw new CloneNotSupportedException("Not support clone: " + that.getClazz().getName());
            }

            // 克隆对象
            frame.getOperandStack().pushRef(that.clone());
        }
    }

}
