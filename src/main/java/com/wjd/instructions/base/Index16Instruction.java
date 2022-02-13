package com.wjd.instructions.base;

import com.wjd.classfile.type.Uint16;
import com.wjd.rtda.stack.Frame;

/**
 * @since 2021/12/1
 */
public class Index16Instruction implements Instruction {

    protected int index;
    protected Uint16 source;

    @Override
    public void execute(Frame frame) {
        //
    }

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        source = reader.readUint16();
        index = source.value();
    }
}
