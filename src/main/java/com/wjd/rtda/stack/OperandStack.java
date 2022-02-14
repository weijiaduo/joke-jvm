package com.wjd.rtda.stack;

import java.util.Arrays;

import com.wjd.rtda.Slot;
import com.wjd.rtda.heap.HeapObject;

/**
 * 操作数栈
 */
public class OperandStack {

    /** 最大栈深度 */
    private static final int defaultMaxStack = 65536;
    /** 操作数栈数组 */
    private Slot[] slots;
    /** 当前栈大小 */
    private int size;

    public OperandStack() {
        this(defaultMaxStack);
    }

    public OperandStack(int maxStack) {
        if (maxStack <= 0) {
            maxStack = defaultMaxStack;
        }
        slots = new Slot[maxStack];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = new Slot();
        }
    }

    public void pushSlot(Slot slot) {
        slots[size].setSlot(slot);
        size++;
    }

    public Slot popSlot() {
        size--;
        Slot slot = slots[size];
        Slot copySlot = new Slot(slot);
        slot.setSlot(null);
        return copySlot;
    }

    public void pushBoolean(boolean val) {
        if (val) {
            pushInt(1);
        } else {
            pushInt(0);
        }
    }

    public boolean popBoolean() {
        return popInt() != 0;
    }

    public void pushInt(int val) {
        Slot.setInt(slots[size], val);
        size++;
    }

    public int popInt() {
        size--;
        Slot slot = slots[size];
        int val = Slot.getInt(slot);
        slot.setSlot(null);
        return val;
    }

    public void pushFloat(float val) {
        Slot.setFloat(slots[size], val);
        size++;
    }

    public float popFloat() {
        size--;
        Slot slot = slots[size];
        float val = Slot.getFloat(slot);
        slot.setSlot(null);
        return val;
    }

    public void pushLong(long val) {
        // long类型占用2个插槽
        Slot.setLong(slots[size + 1], slots[size], val);
        size += 2;
    }

    public long popLong() {
        // long类型占用2个插槽
        size -= 2;
        Slot highSlot = slots[size + 1];
        Slot lowSlot = slots[size];
        long val = Slot.getLong(highSlot, lowSlot);
        highSlot.setSlot(null);
        lowSlot.setSlot(null);
        return val;
    }

    public void pushDouble(double val) {
        // double类型占用2个插槽
        Slot.setDouble(slots[size + 1], slots[size], val);
        size += 2;
    }

    public double popDouble() {
        // double类型占用2个插槽
        size -= 2;
        Slot highSlot = slots[size + 1];
        Slot lowSlot = slots[size];
        double val = Slot.getDouble(highSlot, lowSlot);
        highSlot.setSlot(null);
        lowSlot.setSlot(null);
        return val;
    }

    public void pushRef(HeapObject ref) {
        slots[size].setRef(ref);
        size++;
    }

    public HeapObject popRef() {
        size--;
        Slot slot = slots[size];
        HeapObject val = slot.getRef();
        slot.setRef(null);
        return val;
    }

    public HeapObject getRefFromTop(int n) {
        if (n > size - 1) {
            return null;
        }
        return slots[size -  1 - n].getRef();
    }

    public void clear() {
        size = 0;
        for (Slot slot : slots) {
            slot.setSlot(null);
        }
    }

    @Override
    public String toString() {
        return "OperandStack{" +
                "slots=" + Arrays.asList(slots).subList(0, size) +
                '}';
    }
}
