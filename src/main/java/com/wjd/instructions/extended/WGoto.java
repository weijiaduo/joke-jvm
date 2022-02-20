package com.wjd.instructions.extended;

import com.wjd.instructions.base.BranchInstruction;
import com.wjd.instructions.base.ByteCodeReader;
import com.wjd.rtda.stack.Frame;

/**
 * goto 跳转
 * @since 2022/1/29
 */
public class WGoto extends BranchInstruction {

    @Override
    public void execute(Frame frame) {
        branch(frame);
    }

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        // 注意是32位有符号整数
        offset = reader.readInt();
    }
}
