package com.wjd.naive.sun.reflect;

import com.wjd.instructions.references.InitClass;
import com.wjd.naive.NativeClass;
import com.wjd.naive.NativeMethod;
import com.wjd.naive.NativeRegistry;
import com.wjd.rtda.Slot;
import com.wjd.rtda.heap.Heap;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.MethodMeta;
import com.wjd.rtda.stack.Frame;
import com.wjd.util.MethodHelper;

/**
 * @since 2022/2/17
 */
public class NativeConstructorAccessorImpl implements NativeClass {

    static {
        NativeRegistry.registry("sun/reflect/NativeConstructorAccessorImpl", "newInstance0",
                "(Ljava/lang/reflect/Constructor;[Ljava/lang/Object;)Ljava/lang/Object;",
                new NewInstance0());
    }

    /**
     * private static native Object newInstance0(Constructor<?> c, Object[] os)
     * throws InstantiationException, IllegalArgumentException, InvocationTargetException;
     */
    static class NewInstance0 implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            HeapObject constructorObj = frame.getLocalVars().getRef(0);
            HeapObject argsArr = frame.getLocalVars().getRef(1);

            MethodMeta constructorMethod = MethodHelper.getObjConstructor(constructorObj);
            ClassMeta constructorClass = constructorMethod.getClazz();

            // 先进行类初始化
            if (!constructorClass.isInitStarted()) {
                frame.revertNextPc();
                InitClass.initClass(frame.getThread(), constructorClass);
                return;
            }

            // 创建实例
            HeapObject obj = Heap.newObject(constructorClass);
            frame.getOpStack().pushRef(obj);

            // 执行<init>方法
            Slot[] args = MethodHelper.convertArgs(obj, argsArr, constructorMethod);
            frame.getThread().invokeMethodWithShim(constructorMethod, args);
        }
    }

}
