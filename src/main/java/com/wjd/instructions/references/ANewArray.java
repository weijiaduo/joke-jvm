package com.wjd.instructions.references;

import com.wjd.instructions.base.Index16Instruction;
import com.wjd.rtda.Frame;
import com.wjd.rtda.heap.Class;
import com.wjd.rtda.heap.ConstantPool;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.heap.cons.ClassRef;

/**
 * 创建引用类型数组对象
 * @since 2022/2/1
 */
public class ANewArray extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        // 数组元素类型
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        ClassRef classRef = (ClassRef) cp.getConstant(index);
        Class componentClass = classRef.resolvedClass();

        int count = frame.getOperandStack().popInt();
        if (count < 0) {
            throw new NegativeArraySizeException("NewArray count: " + count);
        }

        // 数组类型
        Class arrayClass = componentClass.getArrayClass();
        HeapObject arrayObject = arrayClass.newArray(count);
        frame.getOperandStack().pushRef(arrayObject);
    }

}
