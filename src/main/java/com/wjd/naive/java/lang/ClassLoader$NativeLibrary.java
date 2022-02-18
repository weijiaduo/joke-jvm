package com.wjd.naive.java.lang;

import com.wjd.naive.NativeClass;
import com.wjd.naive.NativeMethod;
import com.wjd.naive.NativeRegistry;
import com.wjd.rtda.Slot;
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
            frame.getOperandStack().pushRef(nameObj);
        }
    }

    /**
     * native void load(String name, boolean isBuiltin);
     */
    static class Load implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            HeapObject that = frame.getLocalVars().getThis();
            Slot slot = new Slot();
            Slot.setInt(slot, 1);
            that.setFieldValue("loaded", "Z", slot);
        }
    }

}
