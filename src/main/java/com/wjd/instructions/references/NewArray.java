package com.wjd.instructions.references;

import com.wjd.classfile.type.Uint8;
import com.wjd.instructions.base.ByteCodeReader;
import com.wjd.instructions.base.Instruction;
import com.wjd.rtda.Frame;
import com.wjd.rtda.heap.Class;
import com.wjd.rtda.heap.ClassLoader;
import com.wjd.rtda.heap.HeapObject;

/**
 * 创建基本类型数组对象
 * @since 2022/2/1
 */
public class NewArray implements Instruction {

    private Uint8 atype;

    @Override
    public void execute(Frame frame) {
        int count = frame.getOperandStack().popInt();
        if (count < 0) {
            throw new NegativeArraySizeException("NewArray count: " + count);
        }

        ClassLoader classLoader = frame.getMethod().getClazz().getLoader();
        Class arrayClass = Class.getPrimitiveArrayClass(classLoader, atype);
        HeapObject arrayObject = arrayClass.newArray(count);
        frame.getOperandStack().pushRef(arrayObject);
    }

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        atype = reader.readUint8();
    }

}
