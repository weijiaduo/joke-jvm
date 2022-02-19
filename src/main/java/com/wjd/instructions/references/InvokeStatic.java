package com.wjd.instructions.references;

import com.wjd.instructions.base.Index16Instruction;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.ConstantPool;
import com.wjd.rtda.meta.cons.MethodRef;
import com.wjd.rtda.meta.MethodMeta;

/**
 * 执行静态方法
 * @since 2022/2/7
 */
public class InvokeStatic extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        MethodRef methodRef = (MethodRef) cp.getConstant(index);
        MethodMeta method = methodRef.resolvedMethod();

        // 类初始化
        ClassMeta methodClazz = method.getClazz();
        if (!methodClazz.isInitStarted()) {
            frame.revertNextPc();
            InitClass.initClass(frame.getThread(), methodClazz);
            return;
        }

        if (!method.isStatic()) {
            throw new IncompatibleClassChangeError("Invoke static method: " + method.getName());
        }

        frame.getThread().invokeMethod(method);
    }
}
