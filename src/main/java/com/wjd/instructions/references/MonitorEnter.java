package com.wjd.instructions.references;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.stack.Frame;

/**
 * 加锁
 * @since 2022/2/16
 */
public class MonitorEnter extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        HeapObject that = frame.getOperandStack().popRef();
        if (that == null) {
            throw new NullPointerException("MonitorEnter object is null!");
        }
    }

}