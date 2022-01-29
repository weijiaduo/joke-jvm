package com.wjd.classfile.attr;

import com.wjd.classfile.type.Uint16;

/**
 * 启动方法信息
 */
public class BootstrapMethod {

    private Uint16 bootstrapMethodRef;
    private Uint16[] bootstrapArguments;

    public Uint16 getBootstrapMethodRef() {
        return bootstrapMethodRef;
    }

    public void setBootstrapMethodRef(Uint16 bootstrapMethodRef) {
        this.bootstrapMethodRef = bootstrapMethodRef;
    }

    public Uint16[] getBootstrapArguments() {
        return bootstrapArguments;
    }

    public void setBootstrapArguments(Uint16[] bootstrapArguments) {
        this.bootstrapArguments = bootstrapArguments;
    }
}
