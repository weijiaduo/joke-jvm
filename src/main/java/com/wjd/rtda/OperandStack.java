package com.wjd.rtda;

import java.util.Arrays;
import com.wjd.rtda.heap.Object;

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
        Slot slot = new Slot();
        slot.setSlot(slots[size]);
        slots[size].setSlot(null);
        return slot;
    }

    public void pushInt(int val) {
        Slot.setInt(slots[size], val);
        size++;
    }

    public int popInt() {
        size--;
        return Slot.getInt(slots[size]);
    }

    public void pushFloat(float val) {
        Slot.setFloat(slots[size], val);
        size++;
    }

    public float popFloat() {
        size--;
        return Slot.getFloat(slots[size]);
    }

    public void pushLong(long val) {
        // long类型占用2个插槽
        Slot.setLong(slots[size + 1], slots[size], val);
        size += 2;
    }

    public long popLong() {
        // long类型占用2个插槽
        size -= 2;
        return Slot.getLong(slots[size + 1], slots[size]);
    }

    public void pushDouble(double val) {
        // double类型占用2个插槽
        Slot.setDouble(slots[size + 1], slots[size], val);
        size += 2;
    }

    public double popDouble() {
        // double类型占用2个插槽
        size -= 2;
        return Slot.getDouble(slots[size + 1], slots[size]);
    }

    public void pushRef(Object ref) {
        slots[size].setRef(ref);
        size++;
    }

    public Object popRef() {
        size--;
        Object ref = slots[size].getRef();
        slots[size].setRef(null);
        return ref;
    }

    @Override
    public String toString() {
        return "OperandStack{" +
                "slots=" + Arrays.asList(slots).subList(0, size) +
                '}';
    }
}
