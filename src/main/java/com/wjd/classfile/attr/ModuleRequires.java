package com.wjd.classfile.attr;

import com.wjd.classfile.type.Uint16;

/**
 * 模块依赖
 */
public class ModuleRequires {

    private Uint16 requiresIndex;
    private Uint16 requiresFlags;
    private Uint16 requiresVersionIndex;

    public Uint16 getRequiresIndex() {
        return requiresIndex;
    }

    public void setRequiresIndex(Uint16 requiresIndex) {
        this.requiresIndex = requiresIndex;
    }

    public Uint16 getRequiresFlags() {
        return requiresFlags;
    }

    public void setRequiresFlags(Uint16 requiresFlags) {
        this.requiresFlags = requiresFlags;
    }

    public Uint16 getRequiresVersionIndex() {
        return requiresVersionIndex;
    }

    public void setRequiresVersionIndex(Uint16 requiresVersionIndex) {
        this.requiresVersionIndex = requiresVersionIndex;
    }
}
