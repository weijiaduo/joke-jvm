package com.wjd.instructions.extended;

import com.wjd.classfile.type.Uint16;
import com.wjd.instructions.base.ByteCodeReader;
import com.wjd.instructions.base.Instruction;
import com.wjd.rtda.Frame;

/**
 * 自增
 *
 * @since 2021/12/5
 */
public class WIInc implements Instruction {

    /**
     * 局部变量索引
     */
    private Uint16 index;
    /**
     * 常量
     */
    private Uint16 value;

    @Override
    public void execute(Frame frame) {
        int val = frame.getLocalVars().getInt(index.value());
        val += value.value();
        frame.getLocalVars().setInt(index.value(), val);
    }

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        index = reader.readUint16();
        value = reader.readUint16();
    }
}
