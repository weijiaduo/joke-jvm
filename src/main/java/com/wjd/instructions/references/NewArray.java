package com.wjd.instructions.references;

import com.wjd.classfile.type.Uint8;
import com.wjd.instructions.base.ByteCodeReader;
import com.wjd.instructions.base.Instruction;
import com.wjd.rtda.meta.ArrayMetaHelper;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.ClassMetaLoader;
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

        ClassMetaLoader classMetaLoader = frame.getMethod().getClazz().getLoader();
        ClassMeta arrayClassMeta = ArrayMetaHelper.getPrimitiveArrayClass(classMetaLoader, atype);
        HeapObject arrayObject = arrayClassMeta.newArray(count);
        frame.getOperandStack().pushRef(arrayObject);
    }

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        atype = reader.readUint8();
    }

}
