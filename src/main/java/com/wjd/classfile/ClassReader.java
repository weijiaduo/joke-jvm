package com.wjd.classfile;

import com.wjd.classfile.type.Uint16;
import com.wjd.classfile.type.Uint32;
import com.wjd.classfile.type.Uint8;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ClassReader {

    private ByteBuffer buf;

    private ClassFile classFile;

    public ClassReader (byte[] bytes) {
        buf = ByteBuffer.wrap(bytes);
        buf.order(ByteOrder.BIG_ENDIAN); // 大端
    }

    public int readInt() {
        return buf.getInt();
    }

    public long readLong() {
        return buf.getLong();
    }

    public float readFloat() {
        return buf.getFloat();
    }

    public double readDouble() {
        return buf.getDouble();
    }

    public Uint8 readUint8() {
        byte b = buf.get();
        int val = 0x0FF & b;
        return new Uint8(val);
    }

    public Uint16 readUint16() {
        short s = buf.getShort();
        int val = 0x0FFFF & s;
        return new Uint16(val);
    }

    public Uint32 readUint32() {
        int i = buf.getInt();
        long val = 0x0FFFFFFFFL & i;
        return new Uint32(val);
    }

    public Uint16[] readUint16s(Uint16 length) {
        Uint16[] table = new Uint16[length.value()];
        for (int i = 0; i < table.length; i++) {
            table[i] = readUint16();
        }
        return table;
    }

    public byte[] readBytes(Uint16 length) {
        int len = length.value();
        byte[] bytes = new byte[len];
        buf.get(bytes);
        return bytes;
    }

    public byte[] readBytes(Uint32 length) {
        int len = (int) length.value();
        byte[] bytes = new byte[len];
        buf.get(bytes);
        return bytes;
    }

    public ClassFile getClassFile() {
        return classFile;
    }

    public void setClassFile(ClassFile classFile) {
        this.classFile = classFile;
    }
}
