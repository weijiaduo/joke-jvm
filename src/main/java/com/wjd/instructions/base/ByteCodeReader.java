package com.wjd.instructions.base;

import com.wjd.classfile.type.Uint16;
import com.wjd.classfile.type.Uint32;
import com.wjd.classfile.type.Uint8;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @since 2021/11/6
 */
public class ByteCodeReader {

    private byte[] bytes;
    private int position;
    private ByteBuffer buf;

    public ByteCodeReader(byte[] bytes) {
        this.bytes = bytes;
        this.position = 0;
        buf = ByteBuffer.wrap(bytes);
        buf.order(ByteOrder.BIG_ENDIAN); // 大端
    }

    public int getPosition() {
        return position;
    }

    public Uint8 readUint8() {
        int val = buf.get();
        position += 1;
        return new Uint8(val);
    }

    public Uint16 readUint16() {
        short s = buf.getShort();
        int val = 0x0FFFF & s;
        position += 2;
        return new Uint16(val);
    }

    public Uint32 readUint32() {
        int i = buf.getInt();
        long val = 0x0FFFFFFFFL & i;
        position += 4;
        return new Uint32(val);
    }

    public byte[] readBytes(Uint32 length) {
        int len = (int) length.value();
        byte[] bytes = new byte[len];
        buf.get(bytes);
        position += len;
        return bytes;
    }

}
