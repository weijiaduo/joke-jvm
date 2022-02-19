package com.wjd.instructions.stores;

import com.wjd.rtda.stack.Frame;

/**
 * @since 2021/12/4
 */
public final class StoreUtil {

    private StoreUtil(){}

    public static void istore(Frame frame, int index) {
        int val = frame.getOpStack().popInt();
        frame.getLocalVars().setInt(index, val);
    }

    public static void lstore(Frame frame, int index) {
        long val = frame.getOpStack().popLong();
        frame.getLocalVars().setLong(index, val);

    }

    public static void fstore(Frame frame, int index) {
        float val = frame.getOpStack().popFloat();
        frame.getLocalVars().setFloat(index, val);
    }

    public static void dstore(Frame frame, int index) {
        double val = frame.getOpStack().popDouble();
        frame.getLocalVars().setDouble(index, val);
    }

    public static void astore(Frame frame, int index) {
        frame.getLocalVars().setRef(index, frame.getOpStack().popRef());
    }

}
