package com.wjd.instructions.stores;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.heap.HeapObject;

/**
 * 按索引给数组元素赋值
 *
 * @since 2021/12/1
 */
public class AAStore extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        HeapObject val = frame.getOpStack().popRef();
        int index = frame.getOpStack().popInt();
        HeapObject arrayObject = frame.getOpStack().popRef();
        if (arrayObject == null) {
            throw new NullPointerException("AAStore");
        }
        if (index < 0 || index > arrayObject.getArrayLength()) {
            throw new ArrayIndexOutOfBoundsException("AAStore");
        }
        arrayObject.getRefs()[index] = val;
    }

}
