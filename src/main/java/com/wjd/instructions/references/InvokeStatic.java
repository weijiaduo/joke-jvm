package com.wjd.instructions.references;

import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.ConstantPool;
import com.wjd.rtda.meta.cons.MethodRef;
import com.wjd.rtda.meta.MethodMeta;

/**
 * 执行静态方法
 * @since 2022/2/7
 */
public class InvokeStatic extends InvokeMethod {

    @Override
    public void execute(Frame frame) {
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        MethodRef methodRef = (MethodRef) cp.getConstant(index);
        MethodMeta methodMeta = methodRef.resolvedMethod();

        // 类初始化
        ClassMeta methodClassMeta = methodMeta.getClazz();
        if (!methodClassMeta.isInitStarted()) {
            frame.revertNextPc();
            InitClass.initClass(frame.getThread(), methodClassMeta);
            return;
        }

        if (!methodMeta.isStatic()) {
            throw new IncompatibleClassChangeError("Invoke static method: " + methodMeta.getName());
        }
        invokeMethod(frame, methodMeta);
    }
}
