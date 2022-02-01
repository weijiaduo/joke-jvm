package com.wjd.classfile.cons;

import com.wjd.classfile.ClassFile;
import com.wjd.classfile.ClassReader;
import com.wjd.classfile.type.Uint16;

/**
 * 名称引用常量
 */
public abstract class NameConstantInfo implements RefConstantInfo {

    protected Uint16 index;

    protected ClassFile classFile;

    @Override
    public void readFrom(ClassReader reader) {
        classFile = reader.getClassFile();

        index = reader.readUint16();
    }

    public Uint16 getIndex() {
        return index;
    }

    public String getName() {
        return classFile.getUTF8String(index);
    }

}
