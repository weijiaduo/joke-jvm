package com.wjd.classfile.attr;

import com.wjd.classfile.type.Uint16;

/**
 * 行号信息
 */
public class LineNumberTableEntry {

    private Uint16 startPC;
    private Uint16 lineNumber;

    public Uint16 getStartPC() {
        return startPC;
    }

    public void setStartPC(Uint16 startPC) {
        this.startPC = startPC;
    }

    public Uint16 getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Uint16 lineNumber) {
        this.lineNumber = lineNumber;
    }
}
