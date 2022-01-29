package com.wjd.classfile.attr;

import com.wjd.classfile.ClassReader;
import com.wjd.classfile.type.Uint16;

/**
 * 异常信息
 */
public class ExceptionTableEntry {

    private Uint16 startPC;
    private Uint16 endPC;
    private Uint16 handlerPC;
    private Uint16 catchPC;

    public void readFrom(ClassReader reader) {
        startPC = reader.readUint16();
        endPC = reader.readUint16();
        handlerPC = reader.readUint16();
        catchPC = reader.readUint16();
    }

    public Uint16 getStartPC() {
        return startPC;
    }

    public void setStartPC(Uint16 startPC) {
        this.startPC = startPC;
    }

    public Uint16 getEndPC() {
        return endPC;
    }

    public void setEndPC(Uint16 endPC) {
        this.endPC = endPC;
    }

    public Uint16 getHandlerPC() {
        return handlerPC;
    }

    public void setHandlerPC(Uint16 handlerPC) {
        this.handlerPC = handlerPC;
    }

    public Uint16 getCatchPC() {
        return catchPC;
    }

    public void setCatchPC(Uint16 catchPC) {
        this.catchPC = catchPC;
    }
}
