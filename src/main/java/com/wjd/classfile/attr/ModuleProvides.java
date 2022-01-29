package com.wjd.classfile.attr;

import com.wjd.classfile.type.Uint16;

/**
 * 提供模块
 */
public class ModuleProvides {

    private Uint16 providesIndex;
    private Uint16[] providesWithIndexTable;

    public Uint16 getProvidesIndex() {
        return providesIndex;
    }

    public void setProvidesIndex(Uint16 providesIndex) {
        this.providesIndex = providesIndex;
    }

    public Uint16[] getProvidesWithIndexTable() {
        return providesWithIndexTable;
    }

    public void setProvidesWithIndexTable(Uint16[] providesWithIndexTable) {
        this.providesWithIndexTable = providesWithIndexTable;
    }
}
