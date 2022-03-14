package com.wjd.jnative.java.lang;

import com.wjd.JvmOptions;
import com.wjd.jnative.NativeClass;
import com.wjd.jnative.NativeMethod;
import com.wjd.jnative.NativeRegistry;
import com.wjd.rtda.Slot;
import com.wjd.rtda.Thread;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.*;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.stack.LocalVars;
import com.wjd.util.ArrayHelper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @since 2022/2/13
 */
public class System implements NativeClass {

    static {
        NativeRegistry.registry("java/lang/System", "arraycopy",
                "(Ljava/lang/Object;ILjava/lang/Object;II)V", new Arraycopy());
        NativeRegistry.registry("java/lang/System", "setOut0",
                "(Ljava/io/PrintStream;)V", new SetOut0());
        NativeRegistry.registry("java/lang/System", "initProperties",
                "(Ljava/util/Properties;)Ljava/util/Properties;", new InitProperties());
        NativeRegistry.registry("java/lang/System", "setIn0",
                "(Ljava/io/InputStream;)V", new SetIn0());
        NativeRegistry.registry("java/lang/System", "setErr0",
                "(Ljava/io/PrintStream;)V", new SetErr0());
        NativeRegistry.registry("java/lang/System", "mapLibraryName",
                "(Ljava/lang/String;)Ljava/lang/String;", new MapLibraryName());
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
            if (!ArrayHelper.checkArrayCopy(src, dest)) {
                throw new ArrayStoreException("Arraycopy: src=" + src + ", dest=" + dest);
            }
            if (srcPos < 0 || destPos < 0 || length < 0
                || srcPos + length > src.getArrayLength()
                || destPos + length > dest.getArrayLength()) {
                throw new IndexOutOfBoundsException("Arraycopy: length=" + length
                        + ", srcPos=" + srcPos + ", destPos=" + destPos);
            }

            ArrayHelper.arraycopy(src, srcPos, dest, destPos, length);
        }
    }

    /**
     * private static native Properties initProperties(Properties props);
     */
    static class InitProperties implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            Thread thread = frame.getThread();

            HeapObject propsObj = frame.getLocalVars().getRef(0);
            frame.getOpStack().pushRef(propsObj);

            MethodMeta setPropMethod = propsObj.getClazz().getInstanceMethod("setProperty",
                    "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;");

            ClassMetaLoader loader = propsObj.getClazz().getLoader();
            Map<java.lang.String, java.lang.String> props = getSysProps(thread.getJvmOptions());
            for (java.lang.String key : props.keySet()) {
                Slot propsSlot = new Slot();
                propsSlot.setRef(propsObj);
                Slot keySlot = new Slot();
                keySlot.setRef(StringPool.getStringObj(loader, key));
                Slot valSlot = new Slot();
                valSlot.setRef(StringPool.getStringObj(loader, props.get(key)));
                Slot[] slots = new Slot[] { propsSlot, keySlot, valSlot };
                thread.invokeMethodWithShim(setPropMethod, slots);
            }
        }

        private Map<java.lang.String, java.lang.String> getSysProps(JvmOptions jvmOptions) {
            Map<java.lang.String, java.lang.String> props = new HashMap<>();
            props.put("java.version", "1.8.0");
            props.put("java.home", jvmOptions.getAbsJavaHome());
            props.put("java.class.version", "52.0");
            props.put("java.class.path", jvmOptions.getClasspath());
            props.put("file.separator", File.separator);
            props.put("path.separator", ";");
            props.put("line.separator", "\n");
            props.put("file.encoding", "UTF-8");
            return props;
        }
    }

    /**
     * private static native void setOut0(PrintStream out);
     */
    static class SetOut0 implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            HeapObject outObj = frame.getLocalVars().getRef(0);
            ClassMeta sysClass = frame.getMethod().getClazz();
            sysClass.setFieldRef("out", "Ljava/io/PrintStream;", outObj);
        }
    }

    /**
     * private static native void setIn0(InputStream in);
     */
    static class SetIn0 implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            HeapObject in = frame.getLocalVars().getRef(0);
            ClassMeta sysClass = frame.getMethod().getClazz();
            sysClass.setFieldRef("in", "Ljava/io/InputStream;", in);
        }
    }

    /**
     * private static native void setErr0(PrintStream err);
     */
    static class SetErr0 implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            HeapObject err = frame.getLocalVars().getRef(0);
            ClassMeta sysClass = frame.getMethod().getClazz();
            sysClass.setFieldRef("err", "Ljava/io/PrintStream;", err);
        }
    }

    /**
     * public static native String mapLibraryName(String libname);
     */
    static class MapLibraryName implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            HeapObject libName = frame.getLocalVars().getRef(0);
            frame.getOpStack().pushRef(libName);
        }
    }

}
