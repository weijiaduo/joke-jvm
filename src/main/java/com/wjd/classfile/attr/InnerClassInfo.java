package com.wjd.classfile.attr;

import com.wjd.classfile.type.Uint16;

/**
 * 内部类信息
 */
public class InnerClassInfo {

    private Uint16 innerClassInfoIndex;
    private Uint16 outerClassInfoIndex;
    private Uint16 innerNameIndex;
    private Uint16 innerClassAccessFlags;

    public Uint16 getInnerClassInfoIndex() {
        return innerClassInfoIndex;
    }

    public void setInnerClassInfoIndex(Uint16 innerClassInfoIndex) {
        this.innerClassInfoIndex = innerClassInfoIndex;
    }

    public Uint16 getOuterClassInfoIndex() {
        return outerClassInfoIndex;
    }

    public void setOuterClassInfoIndex(Uint16 outerClassInfoIndex) {
        this.outerClassInfoIndex = outerClassInfoIndex;
    }

    public Uint16 getInnerNameIndex() {
        return innerNameIndex;
    }

    public void setInnerNameIndex(Uint16 innerNameIndex) {
        this.innerNameIndex = innerNameIndex;
    }

    public Uint16 getInnerClassAccessFlags() {
        return innerClassAccessFlags;
    }

    public void setInnerClassAccessFlags(Uint16 innerClassAccessFlags) {
        this.innerClassAccessFlags = innerClassAccessFlags;
    }
}
