package com.wjd.instructions.references;

import com.wjd.instructions.base.Index16Instruction;
import com.wjd.rtda.heap.Heap;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.ConstantPool;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.cons.ClassRef;

/**
 * @since 2022/2/1
 */
public class New extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        ConstantPool cp = frame.getMethod().getClassMeta().getConstantPool();
        ClassRef classRef = (ClassRef) cp.getConstant(index);
        ClassMeta classMeta = classRef.resolvedClass();

        // 类初始化
        if (!classMeta.isInitStarted()) {
            frame.revertNextPc();
            InitClass.initClass(frame.getThread(), classMeta);
            return;
        }

        if (classMeta.isInterface() || classMeta.isAbstract()) {
            throw new InstantiationError("Class : " + classMeta.getName());
        }

        HeapObject ref = Heap.newObject(classMeta);
        frame.getOpStack().pushRef(ref);
    }
}
