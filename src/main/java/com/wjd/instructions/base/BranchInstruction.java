package com.wjd.instructions.base;

import com.wjd.classfile.type.Uint16;
import com.wjd.rtda.Frame;

/**
 * @since 2021/12/1
 */
public class BranchInstruction implements Instruction {

    private int offset;
    private Uint16 source;

    @Override
    public void execute(Frame frame) {
        //
    }

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        source = reader.readUint16();
        offset = source.value();
    }

}
