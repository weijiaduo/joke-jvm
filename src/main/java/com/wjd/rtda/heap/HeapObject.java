package com.wjd.rtda.heap;

import com.wjd.rtda.Slot;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.FieldMeta;
import com.wjd.util.ArrayHelper;

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

    public String getDataType() {
        if (data == null) {
            return null;
        }
        return data.getClass().getSimpleName();
    }

    public int getArrayLength() {
        return ArrayHelper.getArrayLength(this);
    }

    public boolean isInstanceOf(ClassMeta cls) {
        return cls.isAssignableFrom(clazz);
    }

    public void setFieldRef(String name, String descriptor, HeapObject ref) {
        FieldMeta field = clazz.getInstanceField(name, descriptor);
        getSlots()[field.getSlotId()].setRef(ref);
    }

    public HeapObject getFieldRef(String name, String descriptor) {
        FieldMeta field = clazz.getInstanceField(name, descriptor);
        return getSlots()[field.getSlotId()].getRef();
    }

    public int getFieldInt(String name, String descriptor) {
        FieldMeta field = clazz.getInstanceField(name, descriptor);
        return (int) getSlots()[field.getSlotId()].getNum();
    }

    public void setFieldInt(String name, String descriptor, int val) {
        FieldMeta field = clazz.getInstanceField(name, descriptor);
        getSlots()[field.getSlotId()].setNum(val);
    }

    public long getFieldLong(String name, String descriptor) {
        FieldMeta field = clazz.getInstanceField(name, descriptor);
        Slot[] slots = getSlots();
        Slot highSlot = slots[field.getSlotId()];
        Slot lowSlot = slots[field.getSlotId() + 1];
        return Slot.getLong(highSlot, lowSlot);
    }

    public void setFieldLong(String name, String descriptor, long val) {
        FieldMeta field = clazz.getInstanceField(name, descriptor);
        Slot[] slots = getSlots();
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
        if (clazz.isArray()) {
            return ArrayHelper.cloneArrayData(this);
        } else {
            // Slot[]
            Slot[] slots = getSlots();
            Slot[] newSlots = new Slot[slots.length];
            for (int i = 0; i < newSlots.length; i++) {
                newSlots[i] = new Slot(slots[i]);
            }
            return newSlots;
        }
    }

    public Slot[] getSlots() {
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
}
