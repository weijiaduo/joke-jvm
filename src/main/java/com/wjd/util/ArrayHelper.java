package com.wjd.util;

import com.wjd.classfile.type.Uint8;
import com.wjd.rtda.Slot;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.ClassMetaLoader;
import com.wjd.rtda.meta.PrimitiveMeta;

/**
 * @since 2022/2/13
 */
public class ArrayHelper {

    private static class AType {
        public static final int AT_BOOLEAN = 4;
        public static final int AT_CHAR = 5;
        public static final int AT_FLOAT = 6;
        public static final int AT_DOUBLE = 7;
        public static final int AT_BYTE = 8;
        public static final int AT_SHORT = 9;
        public static final int AT_INT = 10;
        public static final int AT_LONG = 11;
    }

    public static boolean isArray(ClassMeta classMeta) {
        String name = classMeta.getName();
        return name.charAt(0) == '[';
    }

    /**
     * 获取数组类名
     */
    public static String getArrayClassName(String className) {
        return "[" + toDescriptor(className);
    }

    private static String toDescriptor(String className) {
        // 数组类型
        if (className.charAt(0) == '[') {
            return className;
        }
        // 基本类型
        if (PrimitiveMeta.primitiveTypes.containsKey(className)) {
            return PrimitiveMeta.primitiveTypes.get(className);
        }
        // 引用类型
        return "L" + className + ";";
    }

    /**
     * 获取数组元素的类名
     * @param className 数组类名
     * @return 元素的类名
     */
    public static String getComponentClassName(String className) {
        if (className.charAt(0) == '[') {
            String componentTypeDescriptor = className.substring(1);
            return ClassHelper.getClassName(componentTypeDescriptor);
        }
        throw new IllegalArgumentException("Not Array: " + className);
    }

    /**
     * 获取基本类型的数组类型
     */
    public static ClassMeta getPrimitiveArrayClass(ClassMetaLoader loader, Uint8 atype) {
        int val = atype.value();
        switch (val) {
            case AType.AT_BOOLEAN:
                return loader.loadClass("[Z");
            case AType.AT_CHAR:
                return loader.loadClass("[C");
            case AType.AT_FLOAT:
                return loader.loadClass("[F");
            case AType.AT_DOUBLE:
                return loader.loadClass("[D");
            case AType.AT_BYTE:
                return loader.loadClass("[B");
            case AType.AT_SHORT:
                return loader.loadClass("[S");
            case AType.AT_INT:
                return loader.loadClass("[I");
            case AType.AT_LONG:
                return loader.loadClass("[J");
            default:
                throw new IllegalArgumentException("Invalid atype: " + val);
        }
    }

    /**
     * 创建指定类型的数组对象
     * @param name 指定类型
     * @param count 数组长度
     * @return 数组对象
     */
    public static Object makeArray(String name, int count) {
        switch (name) {
            case "[Z":
                return new boolean[count];
            case "[B":
                return new byte[count];
            case "[C":
                return new char[count];
            case "[S":
                return new short[count];
            case "[I":
                return new int[count];
            case "[J":
                return new long[count];
            case "[F":
                return new float[count];
            case "[D":
                return new double[count];
            default:
                return new HeapObject[count];
        }
    }

    /**
     * 验证数组是否可以复制
     */
    public static boolean checkArrayCopy(HeapObject src, HeapObject dest) {
        ClassMeta srcClassMeta = src.getClassMeta();
        ClassMeta destClassMeta = dest.getClassMeta();
        if (!srcClassMeta.isArray() || !destClassMeta.isArray()) {
            return false;
        }
        if (srcClassMeta.getComponentClass().isPrimitive()
            || destClassMeta.getComponentClass().isPrimitive()) {
            return srcClassMeta.getComponentClass() == destClassMeta.getComponentClass();
        }
        return true;
    }

    /**
     * 复制数组
     */
    public static void arraycopy(HeapObject src, int srcPos, HeapObject dest, int destPos, int length) {
        String dataType = src.getDataType();
        switch (dataType) {
            case "boolean[]":
            {
                boolean[] srcData = src.getBooleans();
                boolean[] destData = dest.getBooleans();
                System.arraycopy(srcData, srcPos, destData, destPos, length);
                break;
            }
            case "byte[]":
            {
                byte[] srcData = src.getBytes();
                byte[] destData = dest.getBytes();
                System.arraycopy(srcData, srcPos, destData, destPos, length);
                break;
            }
            case "char[]":
            {
                char[] srcData = src.getChars();
                char[] destData = dest.getChars();
                System.arraycopy(srcData, srcPos, destData, destPos, length);
                break;
            }
            case "short[]":
            {
                short[] srcData = src.getShorts();
                short[] destData = dest.getShorts();
                System.arraycopy(srcData, srcPos, destData, destPos, length);
                break;
            }
            case "int[]":
            {
                int[] srcData = src.getInts();
                int[] destData = dest.getInts();
                System.arraycopy(srcData, srcPos, destData, destPos, length);
                break;
            }
            case "long[]":
            {
                long[] srcData = src.getLongs();
                long[] destData = dest.getLongs();
                System.arraycopy(srcData, srcPos, destData, destPos, length);
                break;
            }
            case "float[]":
            {
                float[] srcData = src.getFloats();
                float[] destData = dest.getFloats();
                System.arraycopy(srcData, srcPos, destData, destPos, length);
                break;
            }
            case "double[]":
            {
                double[] srcData = src.getDoubles();
                double[] destData = dest.getDoubles();
                System.arraycopy(srcData, srcPos, destData, destPos, length);
                break;
            }
            case "HeapObject[]":
            {
                HeapObject[] srcData = src.getRefs();
                HeapObject[] destData = dest.getRefs();
                System.arraycopy(srcData, srcPos, destData, destPos, length);
                break;
            }
            case "Slot[]":
                System.out.println("Unsupported Copy Slots");
                break;
            default:
                throw new IllegalArgumentException("Unknown type: " + dataType);
        }
    }

    /**
     * 获取数组长度
     * @param arr 数组对象
     * @return 数组长度
     */
    public static int getArrayLength(HeapObject arr) {
        String dataType = arr.getDataType();
        if (dataType == null) {
            return -1;
        }
        switch (dataType) {
            case "boolean[]":
                return arr.getBooleans().length;
            case "byte[]":
                return arr.getBytes().length;
            case "char[]":
                return arr.getChars().length;
            case "short[]":
                return arr.getShorts().length;
            case "int[]":
                return arr.getInts().length;
            case "long[]":
                return arr.getLongs().length;
            case "float[]":
                return arr.getFloats().length;
            case "double[]":
                return arr.getDoubles().length;
            case "HeapObject[]":
                return arr.getRefs().length;
            default:
        }
        throw new IllegalStateException("Not array");
    }

    /**
     * 克隆数组数据
     * @param arr 数组对象
     * @return 数组数据
     */
    public static Object cloneArrayData(HeapObject arr) {
        String dataType = arr.getDataType();
        if (dataType == null) {
            return null;
        }
        switch (dataType) {
            case "boolean[]":
                return arr.getBooleans().clone();
            case "byte[]":
                return arr.getBytes().clone();
            case "char[]":
                return arr.getChars().clone();
            case "short[]":
                return arr.getShorts().clone();
            case "int[]":
                return arr.getInts().clone();
            case "long[]":
                return arr.getLongs().clone();
            case "float[]":
                return arr.getFloats().clone();
            case "double[]":
                return arr.getDoubles().clone();
            case "HeapObject[]":
                return arr.getRefs().clone();
            default:
            {
                // Slot[]
                Slot[] slots = arr.getSlots();
                Slot[] newSlots = new Slot[slots.length];
                for (int i = 0; i < newSlots.length; i++) {
                    newSlots[i] = new Slot(slots[i]);
                }
                return newSlots;
            }
        }
    }

}
