package com.wjd.classfile.attr;

import com.wjd.classfile.type.Uint16;

/**
 * 本地变量信息
 */
public class LocalVariableTableEntry {

    private Uint16 startPC;
    private Uint16 length;
    private Uint16 nameIndex;
    private Uint16 descriptorIndex;
    private Uint16 index;

    public Uint16 getStartPC() {
        return startPC;
    }

    public void setStartPC(Uint16 startPC) {
        this.startPC = startPC;
    }

    public Uint16 getLength() {
        return length;
    }

    public void setLength(Uint16 length) {
        this.length = length;
    }

    public Uint16 getNameIndex() {
        return nameIndex;
    }

    public void setNameIndex(Uint16 nameIndex) {
        this.nameIndex = nameIndex;
    }

    public Uint16 getDescriptorIndex() {
        return descriptorIndex;
    }

    public void setDescriptorIndex(Uint16 descriptorIndex) {
        this.descriptorIndex = descriptorIndex;
    }

    public Uint16 getIndex() {
        return index;
    }

    public void setIndex(Uint16 index) {
        this.index = index;
    }
}
