package com.wjd.instructions.loads;

import com.wjd.instructions.base.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.heap.HeapObject;

/**
 * 按索引取数组元素值
 *
 * @since 2021/12/1
 */
public class AALoad extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        int index = frame.getOpStack().popInt();
        HeapObject arrayObject = frame.getOpStack().popRef();
        if (arrayObject == null) {
            throw new NullPointerException("AALoad");
        }
        if (index < 0 || index > arrayObject.getArrayLength()) {
            throw new ArrayIndexOutOfBoundsException("AALoad");
        }
        frame.getOpStack().pushRef(arrayObject.getRefs()[index]);
    }

}
