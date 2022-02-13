package com.wjd.rtda.heap;

import com.wjd.rtda.Slot;
import com.wjd.rtda.meta.FieldMeta;
import com.wjd.rtda.meta.ClassMeta;

/**
 * 对象基类
 * @since 2022/1/30
 */
public class HeapObject {

    private ClassMeta clazz;
    private Object data;
    private Object extra;

    public static HeapObject newObject(ClassMeta clazz) {
        HeapObject obj = new HeapObject();
        obj.clazz = clazz;
        obj.newSlots();
        return obj;
    }

    public static HeapObject newArray(ClassMeta clazz, Object data) {
        HeapObject obj = new HeapObject();
        obj.clazz = clazz;
        obj.data = data;
        return obj;
    }

    private void newSlots() {
        Slot[] fields = new Slot[clazz.getInstanceSlotCount()];
        for (int i = 0; i < fields.length; i++) {
            fields[i] = new Slot();
        }
        data = fields;
    }

    public ClassMeta getClazz() {
        return clazz;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }

    public Slot[] getFields() {
        return (Slot[]) data;
    }

    public boolean[] getBooleans() {
        return (boolean[]) data;
    }

    public byte[] getBytes() {
        return (byte[]) data;
    }

    public char[] getChars() {
        return (char[]) data;
    }

    public short[] getShorts() {
        return (short[]) data;
    }

    public int[] getInts() {
        return (int[]) data;
    }

    public long[] getLongs() {
        return (long[]) data;
    }

    public float[] getFloats() {
        return (float[]) data;
    }

    public double[] getDoubles() {
        return (double[]) data;
    }

    public HeapObject[] getRefs() {
        return (HeapObject[]) data;
    }

    public int getArrayLength() {
        if (data instanceof Slot[]) {
            return getFields().length;
        } else if (data instanceof boolean[]) {
            return getBooleans().length;
        } else if (data instanceof byte[]) {
            return getBytes().length;
        } else if (data instanceof char[]) {
            return getChars().length;
        } else if (data instanceof short[]) {
            return getShorts().length;
        } else if (data instanceof int[]) {
            return getInts().length;
        } else if (data instanceof long[]) {
            return getLongs().length;
        } else if (data instanceof float[]) {
            return getFloats().length;
        } else if (data instanceof double[]) {
            return getDoubles().length;
        } else if (data instanceof HeapObject[]) {
            return getRefs().length;
        }
        throw new IllegalStateException("Not array");
    }

    public boolean isInstanceOf(ClassMeta cls) {
        return cls.isAssignableFrom(clazz);
    }

    public void setRefVar(String name, String descriptor, HeapObject ref) {
        FieldMeta fieldMeta = clazz.getField(name, descriptor, false);
        Slot[] slots = getFields();
        slots[fieldMeta.getSlotId()].setRef(ref);
    }

    public HeapObject getRefVar(String name, String descriptor) {
        FieldMeta fieldMeta = clazz.getField(name, descriptor, false);
        Slot[] slots = getFields();
        return slots[fieldMeta.getSlotId()].getRef();
    }
}
