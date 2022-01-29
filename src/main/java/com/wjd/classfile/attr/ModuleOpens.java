package com.wjd.classfile.attr;

import com.wjd.classfile.type.Uint16;

/**
 * 打开模块
 */
public class ModuleOpens {

    private Uint16 opensIndex;
    private Uint16 opensFlags;
    private Uint16[] opensToIndexTable;

    public Uint16 getOpensIndex() {
        return opensIndex;
    }

    public void setOpensIndex(Uint16 opensIndex) {
        this.opensIndex = opensIndex;
    }

    public Uint16 getOpensFlags() {
        return opensFlags;
    }

    public void setOpensFlags(Uint16 opensFlags) {
        this.opensFlags = opensFlags;
    }

    public Uint16[] getOpensToIndexTable() {
        return opensToIndexTable;
    }

    public void setOpensToIndexTable(Uint16[] opensToIndexTable) {
        this.opensToIndexTable = opensToIndexTable;
    }
}
