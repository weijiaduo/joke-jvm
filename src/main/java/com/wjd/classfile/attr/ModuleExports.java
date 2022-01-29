package com.wjd.classfile.attr;

import com.wjd.classfile.type.Uint16;

/**
 * 导出模块
 */
public class ModuleExports {

    private Uint16 exportsIndex;
    private Uint16 exportsFlags;
    private Uint16[] exportsToIndexTable;

    public Uint16 getExportsIndex() {
        return exportsIndex;
    }

    public void setExportsIndex(Uint16 exportsIndex) {
        this.exportsIndex = exportsIndex;
    }

    public Uint16 getExportsFlags() {
        return exportsFlags;
    }

    public void setExportsFlags(Uint16 exportsFlags) {
        this.exportsFlags = exportsFlags;
    }

    public Uint16[] getExportsToIndexTable() {
        return exportsToIndexTable;
    }

    public void setExportsToIndexTable(Uint16[] exportsToIndexTable) {
        this.exportsToIndexTable = exportsToIndexTable;
    }
}
