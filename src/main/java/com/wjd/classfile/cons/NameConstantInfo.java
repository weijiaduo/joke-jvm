package com.wjd.classfile.cons;

import com.wjd.classfile.ClassReader;
import com.wjd.classfile.type.Uint16;

/**
 * 名称引用常量
 */
public abstract class NameConstantInfo implements RefConstantInfo {

    protected Uint16 index;

    @Override
    public void readFrom(ClassReader reader) {
        index = reader.readUint16();
    }

    public Uint16 getIndex() {
        return index;
    }

}
