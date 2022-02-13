package com.wjd.naive.java.lang;

import com.wjd.naive.NativeClass;
import com.wjd.naive.NativeMethod;
import com.wjd.naive.NativeRegistry;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.ArrayMetaHelper;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.stack.LocalVars;

/**
 * @since 2022/2/13
 */
public class System implements NativeClass {

    static {
        NativeRegistry.registry("java/lang/System", "arraycopy",
                "(Ljava/lang/Object;ILjava/lang/Object;II)V", new Arraycopy());
    }

    /**
     * public static native void arraycopy(
     * Object src, int srcPos, Object dest, int destPos, int length)
     */
    static class Arraycopy implements NativeMethod {
        @Override
        public void execute(Frame frame) {
            LocalVars localVars = frame.getLocalVars();
            HeapObject src = localVars.getRef(0);
            int srcPos = localVars.getInt(1);
            HeapObject dest = localVars.getRef(2);
            int destPos = localVars.getInt(3);
            int length = localVars.getInt(4);

            if (src == null || dest == null) {
                throw new NullPointerException("Arraycopy: src=" + src + ", dest=" + dest);
            }
            if (!ArrayMetaHelper.checkArrayCopy(src, dest)) {
                throw new ArrayStoreException("Arraycopy: src=" + src + ", dest=" + dest);
            }
            if (srcPos < 0 || destPos < 0 || length < 0
                || srcPos + length > src.getArrayLength()
                || destPos + length > dest.getArrayLength()) {
                throw new IndexOutOfBoundsException("Arraycopy: length=" + length
                        + ", srcPos=" + srcPos + ", destPos=" + destPos);
            }

            ArrayMetaHelper.arraycopy(src, srcPos, dest, destPos, length);
        }
    }

}
