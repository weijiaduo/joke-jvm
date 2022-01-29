package com.wjd.rtda;

/**
 * 局部变量插槽
 */
public class Slot {

    /** 索引 */
    private long num;
    /** 引用 */
    private Object ref;

    public Object getRef() {
        return ref;
    }

    public void setRef(Object ref) {
        this.ref = ref;
    }

    public static void setInt(Slot slot, int num) {
        slot.num = num;
    }

    public static int getInt(Slot slot) {
        return (int) slot.num;
    }

    public static void setFloat(Slot slot, float num) {
        int bits = Float.floatToIntBits(num);
        setInt(slot, bits);
    }

    public static float getFloat(Slot slot) {
        int bits = getInt(slot);
        return Float.intBitsToFloat(bits);
    }

    public static void setLong(Slot highSlot, Slot lowSlot, long num) {
        // long类型占用2个插槽
        highSlot.num = num >>> 32;
        lowSlot.num = num & 0x0FFFFFFFFL;
    }

    public static long getLong(Slot highSlot, Slot lowSlot) {
        // long类型占用2个插槽
        long lowerBits = lowSlot.num;
        long highBits = highSlot.num;
        long val = highBits & 0x0FFFFFFFFL;
        val = (val << 32) | (lowerBits & 0x0FFFFFFFFL);
        return val;
    }

    public static void setDouble(Slot highSlot, Slot lowSlot, double num) {
        // 把double的bit转成long保存
        long bits = Double.doubleToLongBits(num);
        setLong(highSlot, lowSlot, bits);
    }

    public static double getDouble(Slot highSlot, Slot lowSlot) {
        // 把long的bit解析成double
        long bits = getLong(highSlot, lowSlot);
        return Double.longBitsToDouble(bits);
    }

}
