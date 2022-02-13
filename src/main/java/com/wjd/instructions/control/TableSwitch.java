package com.wjd.instructions.control;

import com.wjd.instructions.base.BranchInstruction;
import com.wjd.instructions.base.ByteCodeReader;
import com.wjd.rtda.stack.Frame;

/**
 * table switch 跳转
 *
 * tableswitch
 * <0-3 byte pad>
 * defaultbyte1
 * defaultbyte2
 * defaultbyte3
 * defaultbyte4
 * lowbyte1
 * lowbyte2
 * lowbyte3
 * lowbyte4
 * highbyte1
 * highbyte2
 * highbyte3
 * highbyte4
 * jump offsets...
 *
 * @since 2022/1/29
 */
public class TableSwitch extends BranchInstruction {

    private int defaultOffset;
    private int low;
    private int high;
    private int[] jumpOffsets;

    @Override
    public void execute(Frame frame) {
        int index = frame.getOperandStack().popInt();
        if (index >= low && index <= high) {
            offset = jumpOffsets[index - low];
        } else {
            offset = defaultOffset;
        }
        branch(frame);
    }

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        reader.skipPadding();
        defaultOffset = reader.readInt();
        low = reader.readInt();
        high = reader.readInt();
        int jumpOffsetsCount = high - low + 1;
        jumpOffsets = reader.readInts(jumpOffsetsCount);
    }

}
