package com.wjd.instructions.stores;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.heap.HeapObject;

/**
 * 按索引给数组元素赋值
 *
 * @since 2021/12/1
 */
public class LAStore extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        long val = frame.getOperandStack().popLong();
        int index = frame.getOperandStack().popInt();
        HeapObject arrayObject = frame.getOperandStack().popRef();
        if (arrayObject == null) {
            throw new NullPointerException("LAStore");
        }
        if (index < 0 || index > arrayObject.getArrayLength()) {
            throw new ArrayIndexOutOfBoundsException("LAStore");
        }
        arrayObject.getLongs()[index] = val;
    }

}
