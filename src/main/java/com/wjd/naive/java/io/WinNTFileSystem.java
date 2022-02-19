package com.wjd.naive.java.io;

import com.wjd.naive.NativeClass;
import com.wjd.naive.NativeMethod;
import com.wjd.naive.NativeRegistry;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.StringPool;
import com.wjd.rtda.stack.Frame;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @since 2022/2/18
 */
public class WinNTFileSystem implements NativeClass {

    static {
        NativeRegistry.registry("java/io/WinNTFileSystem", "initIDs",
                "()V", new InitIDs());
        NativeRegistry.registry("java/io/WinNTFileSystem", "getBooleanAttributes",
                "(Ljava/io/File;)I", new GetBooleanAttributes());
    }

    /**
     * private static native void initIDs();
     */
    static class InitIDs implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
        }
    }

    /**
     * public native int getBooleanAttributes(File f);
     */
    static class GetBooleanAttributes implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            HeapObject fileObj = frame.getLocalVars().getRef(1);
            HeapObject pathObj = fileObj.getFieldRef("path", "Ljava/lang/String;");
            String pathStr = StringPool.getRawString(pathObj);

            Path path = Paths.get(pathStr);
            File file = path.toFile();

            int attribute = 0;
            if (file.exists()) {
                attribute |= 0x01;
            }
            if (file.isDirectory()) {
                attribute |= 0x04;
            }

            frame.getOpStack().pushInt(attribute);
        }
    }
}
