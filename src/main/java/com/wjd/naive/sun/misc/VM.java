package com.wjd.naive.sun.misc;

import com.wjd.instructions.references.InvokeMethod;
import com.wjd.naive.NativeClass;
import com.wjd.naive.NativeMethod;
import com.wjd.naive.NativeRegistry;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.MethodMeta;
import com.wjd.rtda.meta.StringPool;
import com.wjd.rtda.stack.Frame;

/**
 * @since 2022/2/13
 */
public class VM implements NativeClass {

    static {
        NativeRegistry.registry("sun/misc/VM", "initialize",
                "()V", new Initialize());
    }

    /**
     * TODO: 暂时给VM的savedProps设置一个值，foo=bar
     * private static native void initialize();
     */
    static class Initialize implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            ClassMeta vmClass = frame.getMethod().getClazz();
            HeapObject saveProps = vmClass.getRefVar("savedProps", "Ljava/util/Properties;");
            HeapObject key = StringPool.getObjString(vmClass.getLoader(), "foo");
            HeapObject val = StringPool.getObjString(vmClass.getLoader(), "bar");
            frame.getOperandStack().pushRef(saveProps);
            frame.getOperandStack().pushRef(key);
            frame.getOperandStack().pushRef(val);

            ClassMeta propClassMeta = vmClass.getLoader().loadClass("java/util/Properties");
            MethodMeta setPropMethod = propClassMeta.getInstanceMethod("setProperty",
                    "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;");
            InvokeMethod.invokeMethod(frame, setPropMethod);
        }
    }

}
