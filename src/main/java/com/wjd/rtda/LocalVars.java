package com.wjd.rtda;

import java.util.Arrays;
import com.wjd.rtda.heap.Object;

/**
 * 局部变量表
 */
public class LocalVars {

    /** 局部变量数组的最大大小 */
    private static final int defaultMaxLocals = 65536;
    /** 局部变量表数组 */
    private Slot[] slots;

    public LocalVars() {
        this(defaultMaxLocals);
    }

    public LocalVars(int maxLocals) {
        if (maxLocals <= 0) {
            maxLocals = defaultMaxLocals;
        }
        // 一开始就把插槽初始化好
        slots = new Slot[maxLocals];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = new Slot();
        }
    }

    public void setSlot(int index, Slot slot) {
        slots[index].setSlot(slot);
    }

    public void setInt(int index, int num) {
        Slot.setInt(slots[index], num);
    }

    public int getInt(int index) {
        return Slot.getInt(slots[index]);
    }

    public void setFloat(int index, float num) {
        Slot.setFloat(slots[index], num);
    }

    public float getFloat(int index) {
        return Slot.getFloat(slots[index]);
    }

    public void setLong(int index, long num) {
        // long类型占用2个插槽
        Slot.setLong(slots[index + 1], slots[index], num);
    }

    public long getLong(int index) {
        // long类型占用2个插槽
        return Slot.getLong(slots[index + 1], slots[index]);
    }

    public void setDouble(int index, double num) {
        // double类型占用2个插槽
        Slot.setDouble(slots[index + 1], slots[index], num);
    }

    public double getDouble(int index) {
        // double类型占用2个插槽
        return Slot.getDouble(slots[index + 1], slots[index]);
    }

    public void setRef(int index, Object ref) {
        slots[index].setRef(ref);
    }

    public Object getRef(int index) {
        return slots[index].getRef();
    }

    @Override
    public String toString() {
        return "LocalVars{" +
                "slots=" + Arrays.toString(slots) +
                '}';
    }
}
