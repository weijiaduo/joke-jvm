package com.wjd.instructions.references;

import com.wjd.instructions.base.Index16Instruction;
import com.wjd.rtda.Frame;
import com.wjd.rtda.heap.Class;
import com.wjd.rtda.heap.ConstantPool;
import com.wjd.rtda.heap.Object;
import com.wjd.rtda.heap.cons.ClassRef;

/**
 * @since 2022/2/1
 */
public class New extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        ClassRef classRef = (ClassRef) cp.getConstant(index);
        Class clazz = classRef.resolvedClass();

        // 类初始化
        if (!clazz.isInitStarted()) {
            frame.revertNextPc();
            InitClass.initClass(frame.getThread(), clazz);
            return;
        }

        if (clazz.isInterface() || clazz.isAbstract()) {
            throw new InstantiationError("Class : " + clazz.getName());
        }
        Object ref = clazz.newObject();
        frame.getOperandStack().pushRef(ref);
    }
}
