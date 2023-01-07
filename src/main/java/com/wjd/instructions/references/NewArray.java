package com.wjd.instructions.references;

import com.wjd.classfile.type.Uint8;
import com.wjd.instructions.base.ByteCodeReader;
import com.wjd.instructions.base.Instruction;
import com.wjd.rtda.heap.Heap;
import com.wjd.util.ArrayHelper;
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
        int count = frame.getOpStack().popInt();
        if (count < 0) {
            throw new NegativeArraySizeException("NewArray count: " + count);
        }

        ClassMetaLoader loader = frame.getMethod().getClassMeta().getLoader();
        ClassMeta arrayClassMeta = ArrayHelper.getPrimitiveArrayClass(loader, atype);
        HeapObject arrayObject = Heap.newArray(arrayClassMeta, count);
        frame.getOpStack().pushRef(arrayObject);
    }

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        atype = reader.readUint8();
    }

}
