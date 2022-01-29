package com.wjd.classfile.cons;

import com.wjd.classfile.ClassReader;
import com.wjd.classfile.type.Uint16;

/**
 * 成员引用常量
 */
public abstract class MemberConstantInfo implements RefConstantInfo {

    protected Uint16 classIndex;
    protected Uint16 nameAndTypeIndex;

    @Override
    public void readFrom(ClassReader reader) {
        classIndex = reader.readUint16();
        nameAndTypeIndex = reader.readUint16();
    }

    public Uint16 getClassIndex() {
        return classIndex;
    }

    public Uint16 getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }

}
