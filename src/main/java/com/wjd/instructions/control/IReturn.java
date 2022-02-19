package com.wjd.instructions.control;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.Thread;

/**
 * 返回整数
 * @since 2022/2/7
 */
public class IReturn extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Thread thread = frame.getThread();
        Frame currentFrame = thread.popFrame();
        Frame invokeFrame = thread.topFrame();
        int returnVal = currentFrame.getOpStack().popInt();
        invokeFrame.getOpStack().pushInt(returnVal);
    }
}
