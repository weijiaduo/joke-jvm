package com.wjd.instructions.base;

import com.wjd.rtda.Frame;

/**
 * @since 2021/12/1
 */
public class BranchInstruction implements Instruction {

    /** 16位有符号整数偏移 */
    protected int offset;

    @Override
    public void execute(Frame frame) {
        //
    }

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        // 注意是16位有符号整数
        offset = reader.readShort();
    }

    public void branch(Frame frame) {
        int pc = frame.getThread().getPc();
        frame.setNextPc(pc + offset);
    }

}
