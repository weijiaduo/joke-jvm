package com.wjd.rtda.heap;

import com.wjd.rtda.Slot;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.FieldMeta;

/**
 * 对象基类
 * @since 2022/1/30
 */
public class HeapObject implements Cloneable {

    /** 对象的元数据类型 */
    ClassMeta clazz;
    /** 实例对象的数据 */
    Object data;
    /** 目前是Class对象 */
    Object extra;

    HeapObject(ClassMeta clazz) {
        this.clazz = clazz;
        this.initSlots();
    }

    HeapObject(ClassMeta clazz, Object data) {
        this.clazz = clazz;
        this.data = data;
    }

    void initSlots() {
        Slot[] fields = new Slot[clazz.getInstanceSlotCount()];
        for (int i = 0; i < fields.length; i++) {
            fields[i] = new Slot();
        }
        data = fields;
    }

    public ClassMeta getClazz() {
        return clazz;
    }

    public Object getData() {
        return data;
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

    public String getDataType() {
        if (data == null) {
            return null;
        }
        return data.getClass().getSimpleName();
    }

    public int getArrayLength() {
        String dataType = getDataType();
        if (dataType == null) {
            return -1;
        }
        switch (dataType) {
            case "boolean[]":
                return getBooleans().length;
            case "byte[]":
                return getBytes().length;
            case "char[]":
                return getChars().length;
            case "short[]":
                return getShorts().length;
            case "int[]":
                return getInts().length;
            case "long[]":
                return getLongs().length;
            case "float[]":
                return getFloats().length;
            case "double[]":
                return getDoubles().length;
            case "HeapObject[]":
                return getRefs().length;
            default:
        }
        throw new IllegalStateException("Not array");
    }

    public boolean isInstanceOf(ClassMeta cls) {
        return cls.isAssignableFrom(clazz);
    }

    public void setRefVar(String name, String descriptor, HeapObject ref) {
        FieldMeta field = clazz.getInstanceField(name, descriptor);
        getFields()[field.getSlotId()].setRef(ref);
    }

    public HeapObject getRefVar(String name, String descriptor) {
        FieldMeta field = clazz.getInstanceField(name, descriptor);
        return getFields()[field.getSlotId()].getRef();
    }

    public int getIntVar(String name, String descriptor) {
        FieldMeta field = clazz.getInstanceField(name, descriptor);
        return (int) getFields()[field.getSlotId()].getNum();
    }

    public void setIntVar(String name, String descriptor, int val) {
        FieldMeta field = clazz.getInstanceField(name, descriptor);
        getFields()[field.getSlotId()].setNum(val);
    }

    public long getLongVar(String name, String descriptor) {
        FieldMeta field = clazz.getInstanceField(name, descriptor);
        Slot[] slots = getFields();
        Slot highSlot = slots[field.getSlotId()];
        Slot lowSlot = slots[field.getSlotId() + 1];
        return Slot.getLong(highSlot, lowSlot);
    }

    public void setLongVar(String name, String descriptor, long val) {
        FieldMeta field = clazz.getInstanceField(name, descriptor);
        Slot[] slots = getFields();
        Slot highSlot = slots[field.getSlotId()];
        Slot lowSlot = slots[field.getSlotId() + 1];
        Slot.setLong(highSlot, lowSlot, val);
    }

    @Override
    public HeapObject clone() throws CloneNotSupportedException {
        HeapObject cloneObj = (HeapObject) super.clone();
        if (data != null) {
            cloneObj.data = cloneData();
        }
        return cloneObj;
    }

    private Object cloneData() {
        String dataType = getDataType();
        if (dataType == null) {
            return null;
        }
        switch (dataType) {
            case "boolean[]":
                return getBooleans().clone();
            case "byte[]":
                return getBytes().clone();
            case "char[]":
                return getChars().clone();
            case "short[]":
                return getShorts().clone();
            case "int[]":
                return getInts().clone();
            case "long[]":
                return getLongs().clone();
            case "float[]":
                return getFloats().clone();
            case "double[]":
                return getDoubles().clone();
            case "HeapObject[]":
                return getRefs().clone();
            default:
            {
                // Slot[]
                Slot[] slots = getFields();
                Slot[] newSlots = new Slot[slots.length];
                for (int i = 0; i < newSlots.length; i++) {
                    newSlots[i] = new Slot(slots[i]);
                }
                return newSlots;
            }
        }
    }
}
