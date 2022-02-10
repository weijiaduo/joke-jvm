package com.wjd.instructions.loads;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.Frame;
import com.wjd.rtda.heap.HeapObject;

/**
 * 按索引取数组元素值
 *
 * @since 2021/12/1
 */
public class IALoad extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        int index = frame.getOperandStack().popInt();
        HeapObject arrayObject = frame.getOperandStack().popRef();
        if (arrayObject == null) {
            throw new NullPointerException("IALoad");
        }
        if (index < 0 || index > arrayObject.getArrayLength()) {
            throw new ArrayIndexOutOfBoundsException("IALoad");
        }
        frame.getOperandStack().pushInt(arrayObject.getInts()[index]);
    }

}
