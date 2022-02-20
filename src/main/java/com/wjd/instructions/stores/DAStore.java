package com.wjd.instructions.stores;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.heap.HeapObject;

/**
 * 按索引给数组元素赋值
 *
 * @since 2021/12/1
 */
public class DAStore extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        double val = frame.getOpStack().popDouble();
        int index = frame.getOpStack().popInt();
        HeapObject arrayObject = frame.getOpStack().popRef();
        if (arrayObject == null) {
            throw new NullPointerException("DAStore");
        }
        if (index < 0 || index > arrayObject.getArrayLength()) {
            throw new ArrayIndexOutOfBoundsException("DAStore");
        }
        arrayObject.getDoubles()[index] = val;
    }

}
