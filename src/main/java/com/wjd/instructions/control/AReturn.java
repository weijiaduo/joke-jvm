package com.wjd.instructions.control;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.Frame;
import com.wjd.rtda.Thread;
import com.wjd.rtda.heap.HeapObject;

/**
 * 返回对象引用
 * @since 2022/2/7
 */
public class AReturn extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Thread thread = frame.getThread();
        Frame currentFrame = thread.popFrame();
        Frame invokeFrame = thread.topFrame();
        HeapObject returnVal = currentFrame.getOperandStack().popRef();
        invokeFrame.getOperandStack().pushRef(returnVal);
    }
}
