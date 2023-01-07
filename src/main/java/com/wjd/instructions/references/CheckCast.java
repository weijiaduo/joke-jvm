package com.wjd.instructions.references;

import com.wjd.instructions.base.Index16Instruction;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.stack.OperandStack;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.ConstantPool;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.cons.ClassRef;

/**
 * @since 2022/2/2
 */
public class CheckCast extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOpStack();
        HeapObject ref = stack.popRef();
        stack.pushRef(ref);
        if (ref == null) {
            return;
        }

        ConstantPool cp = frame.getMethod().getClassMeta().getConstantPool();
        ClassRef classRef = (ClassRef) cp.getConstant(index);
        ClassMeta classMeta = classRef.resolvedClass();
        if (!ref.isInstanceOf(classMeta)) {
            throw new ClassCastException("Cast Class: " + classMeta);
        }
    }
}
