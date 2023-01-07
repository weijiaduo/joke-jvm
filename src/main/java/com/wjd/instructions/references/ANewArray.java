package com.wjd.instructions.references;

import com.wjd.instructions.base.Index16Instruction;
import com.wjd.rtda.heap.Heap;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.ConstantPool;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.cons.ClassRef;

/**
 * 创建引用类型数组对象
 * @since 2022/2/1
 */
public class ANewArray extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        // 数组元素类型
        ConstantPool cp = frame.getMethod().getClassMeta().getConstantPool();
        ClassRef classRef = (ClassRef) cp.getConstant(index);
        ClassMeta componentClassMeta = classRef.resolvedClass();

        int count = frame.getOpStack().popInt();
        if (count < 0) {
            throw new NegativeArraySizeException("NewArray count: " + count);
        }

        // 数组类型
        ClassMeta arrayClassMeta = componentClassMeta.getArrayClass();
        HeapObject arrayObject = Heap.newArray(arrayClassMeta, count);
        frame.getOpStack().pushRef(arrayObject);
    }

}
