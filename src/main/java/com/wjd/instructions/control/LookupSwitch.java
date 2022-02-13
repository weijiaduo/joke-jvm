package com.wjd.instructions.control;

import com.wjd.instructions.base.BranchInstruction;
import com.wjd.instructions.base.ByteCodeReader;
import com.wjd.rtda.stack.Frame;

/**
 * @since 2022/1/29
 */
public class LookupSwitch extends BranchInstruction {

    private int defaultOffset;
    private int npairs;
    private int[] matchOffsets;

    @Override
    public void execute(Frame frame) {
        int key = frame.getOperandStack().popInt();
        offset = defaultOffset;
        for (int i = 0; i < npairs * 2; i += 2) {
            if (matchOffsets[i] == key) {
                offset = matchOffsets[i + 1];
                break;
            }
        }
        branch(frame);
    }

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        reader.skipPadding();
        defaultOffset = reader.readInt();
        npairs = reader.readInt();
        matchOffsets = reader.readInts(npairs * 2);
    }
}
