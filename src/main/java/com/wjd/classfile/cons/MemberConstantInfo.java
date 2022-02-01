package com.wjd.classfile.cons;

import com.wjd.classfile.ClassFile;
import com.wjd.classfile.ClassReader;
import com.wjd.classfile.type.Uint16;

/**
 * 成员引用常量
 */
public abstract class MemberConstantInfo implements RefConstantInfo {

    protected Uint16 classIndex;
    protected Uint16 nameAndTypeIndex;

    protected ClassFile classFile;

    @Override
    public void readFrom(ClassReader reader) {
        classFile = reader.getClassFile();

        classIndex = reader.readUint16();
        nameAndTypeIndex = reader.readUint16();
    }

    public String getClassName() {
        ClassConstantInfo classConstantInfo = (ClassConstantInfo) classFile.getConstantInfo(classIndex);
        return classConstantInfo.getName();
    }

    public String getName() {
        NameAndTypeConstantInfo constantInfo = (NameAndTypeConstantInfo) classFile.getConstantInfo(nameAndTypeIndex);
        return constantInfo.getName();
    }

    public String getDescriptor() {
        NameAndTypeConstantInfo constantInfo = (NameAndTypeConstantInfo) classFile.getConstantInfo(nameAndTypeIndex);
        return constantInfo.getDescriptor();
    }

}
