package com.wjd.naive.sun.misc;

import com.wjd.naive.Memory;
import com.wjd.naive.NativeClass;
import com.wjd.naive.NativeMethod;
import com.wjd.naive.NativeRegistry;
import com.wjd.rtda.Slot;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.stack.Frame;

/**
 * @since 2022/2/15
 */
public class Unsafe implements NativeClass {

    static {
        NativeRegistry.registry("sun/misc/Unsafe", "arrayBaseOffset",
                "(Ljava/lang/Class;)I", new ArrayBaseOffset());
        NativeRegistry.registry("sun/misc/Unsafe", "arrayIndexScale",
                "(Ljava/lang/Class;)I", new ArrayIndexScale());
        NativeRegistry.registry("sun/misc/Unsafe", "addressSize",
                "()I", new AddressSize());
        NativeRegistry.registry("sun/misc/Unsafe", "objectFieldOffset",
                "(Ljava/lang/reflect/Field;)J", new ObjectFieldOffset());
        NativeRegistry.registry("sun/misc/Unsafe", "compareAndSwapObject",
                "(Ljava/lang/Object;JLjava/lang/Object;Ljava/lang/Object;)Z", new CompareAndSwapObject());
        NativeRegistry.registry("sun/misc/Unsafe", "getIntVolatile",
                "(Ljava/lang/Object;J)I", new GetIntVolatile());
        NativeRegistry.registry("sun/misc/Unsafe", "compareAndSwapInt",
                "(Ljava/lang/Object;JII)Z", new CompareAndSwapInt());
        NativeRegistry.registry("sun/misc/Unsafe", "allocateMemory",
                "(J)J", new AllocateMemory());
        NativeRegistry.registry("sun/misc/Unsafe", "putLong",
                "(JJ)V", new PutLong());
        NativeRegistry.registry("sun/misc/Unsafe", "getByte",
                "(J)B", new GetByte());
        NativeRegistry.registry("sun/misc/Unsafe", "freeMemory",
                "(J)V", new FreeMemory());
    }

    /**
     * public native int arrayBaseOffset(Class<?> arrayClass);
     */
    static class ArrayBaseOffset implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            frame.getOpStack().pushInt(0);
        }
    }

    /**
     * public native int arrayIndexScale(Class<?> arrayClass);
     */
    static class ArrayIndexScale implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            frame.getOpStack().pushInt(1);
        }
    }

    /**
     * public native int addressSize();
     */
    static class AddressSize implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            frame.getOpStack().pushInt(8);
        }
    }

    /**
     * public native long objectFieldOffset(Field field);
     */
    static class ObjectFieldOffset implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            HeapObject jField = frame.getLocalVars().getRef(1);
            int offset = jField.getIntVar("slot", "I");
            frame.getOpStack().pushLong(offset);
        }
    }

    /**
     * public final native boolean compareAndSwapObject(Object o, long offset, Object expected, Object x)
     */
    static class CompareAndSwapObject implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            HeapObject obj = frame.getLocalVars().getRef(1);
            long offset = frame.getLocalVars().getLong(2);
            HeapObject expected = frame.getLocalVars().getRef(4);
            HeapObject update = frame.getLocalVars().getRef(5);

            Object data = obj.getData();
            if (data instanceof Slot[]) {
                // 对象
                Slot[] fields = (Slot[]) data;
                boolean swapped = casObj(obj, fields, offset, expected, update);
                frame.getOpStack().pushBoolean(swapped);
            } else if (data instanceof HeapObject[]) {
                // 数组
                HeapObject[] arr = (HeapObject[]) data;
                boolean swapped = casArr(arr, offset, expected, update);
                frame.getOpStack().pushBoolean(swapped);
            } else {
                throw new IllegalArgumentException("CompareAndSwapObject data type: " + data);
            }
        }

        private boolean casObj(HeapObject obj, Slot[] fields, long offset, HeapObject expected, HeapObject update) {
            int index = (int) offset;
            HeapObject current = fields[index].getRef();
            if (current == expected) {
                fields[index].setRef(update);
                return true;
            } else {
                return false;
            }
        }

        private boolean casArr(HeapObject[] arr, long offset, HeapObject expected, HeapObject update) {
            int index = (int) offset;
            HeapObject current = arr[index];
            if (current == expected) {
                arr[index] = update;
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * public native int getIntVolatile(Object o, long offset);
     */
    static class GetIntVolatile implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            HeapObject obj = frame.getLocalVars().getRef(1);
            long offset = frame.getLocalVars().getLong(2);
            int index = (int) offset;

            Object data = obj.getData();
            if (data instanceof Slot[]) {
                // Object
                Slot[] slots = (Slot[]) data;
                frame.getOpStack().pushInt(Slot.getInt(slots[index]));
            } else if (data instanceof int[]) {
                // int[]
                int[] ints = (int[]) data;
                frame.getOpStack().pushInt(ints[index]);
            } else {
                throw new IllegalArgumentException("Not int type: " + data);
            }
        }
    }

    /**
     * public final native boolean compareAndSwapInt(Object o, long offset, int expected, int x);
     */
    static class CompareAndSwapInt implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            HeapObject obj = frame.getLocalVars().getRef(1);
            long offset = frame.getLocalVars().getLong(2);
            int expected = frame.getLocalVars().getInt(4);
            int update = frame.getLocalVars().getInt(5);

            Object data = obj.getData();
            if (data instanceof Slot[]) {
                // 对象
                Slot[] fields = (Slot[]) data;
                boolean swapped = casObj(obj, fields, offset, expected, update);
                frame.getOpStack().pushBoolean(swapped);
            } else if (data instanceof int[]) {
                // 数组
                int[] arr = (int[]) data;
                boolean swapped = casArr(arr, offset, expected, update);
                frame.getOpStack().pushBoolean(swapped);
            } else {
                throw new IllegalArgumentException("CompareAndSwapInt data type: " + data);
            }
        }

        private boolean casObj(HeapObject obj, Slot[] fields, long offset, int expected, int update) {
            int index = (int) offset;
            int current = Slot.getInt(fields[index]);
            if (current == expected) {
                Slot.setInt(fields[index], update);
                return true;
            } else {
                return false;
            }
        }

        private boolean casArr(int[] arr, long offset, int expected, int update) {
            int index = (int) offset;
            int current = arr[index];
            if (current == expected) {
                arr[index] = update;
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * public native long allocateMemory(long bytes);
     */
    static class AllocateMemory implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            long bytes = frame.getLocalVars().getLong(1);
            long address = Memory.allocate(bytes);
            frame.getOpStack().pushLong(address);
        }
    }

    /**
     * public native void putLong(long address, long x);
     */
    static class PutLong implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            long address = frame.getLocalVars().getLong(1);
            long x = frame.getLocalVars().getLong(3);
            Memory.putLong(address, x);
        }
    }

    /**
     * public native byte getByte(long address);
     */
    static class GetByte implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            long address = frame.getLocalVars().getLong(1);
            frame.getOpStack().pushInt(Memory.getByte(address));
        }
    }

    /**
     * public native void freeMemory(long address);
     */
    static class FreeMemory implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            long address = frame.getLocalVars().getLong(1);
            Memory.freeMemory(address);
        }
    }

}
