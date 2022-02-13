package com.wjd.instructions.math;

import com.wjd.classfile.type.Uint8;
import com.wjd.instructions.base.ByteCodeReader;
import com.wjd.instructions.base.Instruction;
import com.wjd.rtda.stack.Frame;

/**
 * 自增
 *
 * @since 2021/12/5
 */
public class IInc implements Instruction {

    /**
     * 局部变量索引
     */
    private Uint8 index;
    /**
     * 常量
     */
    private Uint8 value;

    @Override
    public void execute(Frame frame) {
        int val = frame.getLocalVars().getInt(index.value());
        val += value.value();
        frame.getLocalVars().setInt(index.value(), val);
    }

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        index = reader.readUint8();
        value = reader.readUint8();
    }
}
