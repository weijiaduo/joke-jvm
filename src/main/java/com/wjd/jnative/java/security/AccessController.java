package com.wjd.jnative.java.security;

import com.wjd.jnative.NativeClass;
import com.wjd.jnative.NativeMethod;
import com.wjd.jnative.NativeRegistry;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.MethodMeta;
import com.wjd.rtda.stack.Frame;

/**
 * @since 2022/2/15
 */
public class AccessController implements NativeClass {

    static {
        NativeRegistry.registry("java/security/AccessController", "doPrivileged",
                "(Ljava/security/PrivilegedExceptionAction;)Ljava/lang/Object;", new DoPrivileged());
        NativeRegistry.registry("java/security/AccessController", "doPrivileged",
                "(Ljava/security/PrivilegedAction;)Ljava/lang/Object;", new DoPrivileged2());
        NativeRegistry.registry("java/security/AccessController", "getStackAccessControlContext",
                "()Ljava/security/AccessControlContext;", new GetStackAccessControlContext());
    }

    static void doPrivileged0(Frame frame) {
        HeapObject action = frame.getLocalVars().getRef(0);
        frame.getOpStack().pushRef(action);

        MethodMeta method = action.getClazz().getInstanceMethod("run", "()Ljava/lang/Object;");
        frame.getThread().invokeMethod(method);
    }

    /**
     * public static native <T> T doPrivileged(PrivilegedAction<T> action);
     */
    static class DoPrivileged implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            doPrivileged0(frame);
        }
    }

    /**
     * public static native <T> T doPrivileged(PrivilegedAction<T> action);
     */
    static class DoPrivileged2 implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            doPrivileged0(frame);
        }
    }

    /**
     * private static native AccessControlContext getStackAccessControlContext();
     */
    static class GetStackAccessControlContext implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            frame.getOpStack().pushRef(null);
        }
    }

}
