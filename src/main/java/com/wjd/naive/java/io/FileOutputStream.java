package com.wjd.naive.java.io;

import com.wjd.naive.NativeClass;
import com.wjd.naive.NativeMethod;
import com.wjd.naive.NativeRegistry;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.stack.LocalVars;

import java.util.Arrays;

/**
 * @since 2022/2/15
 */
public class FileOutputStream implements NativeClass {

    static {
        NativeRegistry.registry("java/io/FileOutputStream", "writeBytes",
                "([BIIZ)V", new WriteBytes());
        NativeRegistry.registry("java/io/FileOutputStream", "initIDs",
                "()V", new InitIDs());
    }

    /**
     * private native void writeBytes(byte b[], int off, int len, boolean append)
     *         throws IOException;
     */
    static class WriteBytes implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            LocalVars vars = frame.getLocalVars();
            // HeapObject that = vars.getRef(0);
            HeapObject bytesObj = vars.getRef(1);
            int off = vars.getInt(2);
            int len = vars.getInt(3);
            // boolean append = vars.getBoolean(4);
            byte[] bytes = bytesObj.getBytes();
            bytes = Arrays.copyOfRange(bytes, off, off + len);
            System.out.write(bytes);
        }
    }

    /**
     * private static native void initIDs();
     */
    static class InitIDs implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
        }
    }

}
