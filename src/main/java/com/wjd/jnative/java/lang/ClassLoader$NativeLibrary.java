package com.wjd.jnative.java.lang;

import com.wjd.jnative.NativeClass;
import com.wjd.jnative.NativeMethod;
import com.wjd.jnative.NativeRegistry;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.stack.Frame;

/**
 * @since 2022/2/18
 */
public class ClassLoader$NativeLibrary implements NativeClass {

    static {
        NativeRegistry.registry("java/lang/ClassLoader$NativeLibrary", "findBuiltinLib",
                "(Ljava/lang/String;)Ljava/lang/String;", new FindBuiltinLib());
        NativeRegistry.registry("java/lang/ClassLoader$NativeLibrary", "load",
                "(Ljava/lang/String;Z)V", new Load());
    }

    /**
     * static native String findBuiltinLib(String name);
     */
    static class FindBuiltinLib implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            HeapObject nameObj = frame.getLocalVars().getRef(0);
            frame.getOpStack().pushRef(nameObj);
        }
    }

    /**
     * native void load(String name, boolean isBuiltin);
     */
    static class Load implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            HeapObject that = frame.getLocalVars().getThis();
            that.setFieldInt("loaded", "Z", 1);
        }
    }

}
