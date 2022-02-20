package com.wjd.instructions.references;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.heap.HeapObject;

/**
 * 获取数组长度
 * @since 2022/2/11
 */
public class ArrayLength extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        HeapObject arrayObject = frame.getOpStack().popRef();
        if (arrayObject == null) {
            throw new NullPointerException("Array Length");
        }
        int len = arrayObject.getArrayLength();
        frame.getOpStack().pushInt(len);
    }

}
