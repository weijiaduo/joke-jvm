package com.wjd.instructions.loads;

import com.wjd.rtda.Frame;

/**
 * @since 2021/12/4
 */
public final class LoadUtil {

    private LoadUtil(){}

    public static void iload(Frame frame, int index) {
        int val = frame.getLocalVars().getInt(index);
        frame.getOperandStack().pushInt(val);
    }

    public static void lload(Frame frame, int index) {
        long val = frame.getLocalVars().getLong(index);
        frame.getOperandStack().pushLong(val);
    }

    public static void fload(Frame frame, int index) {
        float val = frame.getLocalVars().getFloat(index);
        frame.getOperandStack().pushFloat(val);
    }

    public static void dload(Frame frame, int index) {
        double val = frame.getLocalVars().getDouble(index);
        frame.getOperandStack().pushDouble(val);
    }

    public static void aload(Frame frame, int index) {
        Object val = frame.getLocalVars().getRef(index);
        frame.getOperandStack().pushRef(val);
    }

}
