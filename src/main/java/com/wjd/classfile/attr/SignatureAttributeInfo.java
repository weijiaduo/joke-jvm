package com.wjd.classfile.attr;

import com.wjd.classfile.ClassFile;
import com.wjd.classfile.ClassReader;
import com.wjd.classfile.type.Uint16;
import com.wjd.classfile.type.Uint32;

/**
 * 签名属性
 *
 * Signature_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 signature_index;
 * }
 */
public class SignatureAttributeInfo implements AttributeInfo {

    private Uint32 length;
    private Uint16 signatureIndex;

    private ClassFile classFile;

    @Override
    public void readFrom(ClassReader reader) {
        classFile = reader.getClassFile();

        length = reader.readUint32();
        signatureIndex = reader.readUint16();
    }

    public Uint32 getLength() {
        return length;
    }

    public Uint16 getSignatureIndex() {
        return signatureIndex;
    }

    public String getSignature() {
        return classFile.getUTF8String(signatureIndex);
    }
}
