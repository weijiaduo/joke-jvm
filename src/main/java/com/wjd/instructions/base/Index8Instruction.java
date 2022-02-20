package com.wjd.instructions.base;

import com.wjd.classfile.type.Uint8;
import com.wjd.rtda.stack.Frame;

/**
 * @since 2021/12/1
 */
public class Index8Instruction implements Instruction {

    protected int index;
    protected Uint8 source;

    @Override
    public void execute(Frame frame) {
    }

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        source = reader.readUint8();
        index = source.value();
    }
}
