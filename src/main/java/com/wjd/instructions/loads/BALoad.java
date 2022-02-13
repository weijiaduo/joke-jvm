package com.wjd.instructions.loads;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.heap.HeapObject;

/**
 * 按索引取数组元素值
 *
 * @since 2021/12/1
 */
public class BALoad extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        int index = frame.getOperandStack().popInt();
        HeapObject arrayObject = frame.getOperandStack().popRef();
        if (arrayObject == null) {
            throw new NullPointerException("BALoad");
        }
        if (index < 0 || index > arrayObject.getArrayLength()) {
            throw new ArrayIndexOutOfBoundsException("BALoad");
        }
        frame.getOperandStack().pushInt(arrayObject.getBytes()[index]);
    }

}
