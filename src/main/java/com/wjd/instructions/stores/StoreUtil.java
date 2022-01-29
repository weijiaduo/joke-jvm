package com.wjd.instructions.stores;

import com.wjd.rtda.Frame;

/**
 * @since 2021/12/4
 */
public final class StoreUtil {

    private StoreUtil(){}

    public static void istore(Frame frame, int index) {
        int val = frame.getOperandStack().popInt();
        frame.getLocalvars().setInt(index, val);
    }

    public static void lstore(Frame frame, int index) {
        long val = frame.getOperandStack().popLong();
        frame.getLocalvars().setLong(index, val);

    }

    public static void fstore(Frame frame, int index) {
        float val = frame.getOperandStack().popFloat();
        frame.getLocalvars().setFloat(index, val);
    }

    public static void dstore(Frame frame, int index) {
        double val = frame.getOperandStack().popDouble();
        frame.getLocalvars().setDouble(index, val);
    }

    public static void astore(Frame frame, int index) {
        Object val = frame.getOperandStack().popRef();
        frame.getLocalvars().setRef(index, val);
    }

}
