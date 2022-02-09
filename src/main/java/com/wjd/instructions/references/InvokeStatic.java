package com.wjd.instructions.references;

import com.wjd.rtda.Frame;
import com.wjd.rtda.heap.Class;
import com.wjd.rtda.heap.ConstantPool;
import com.wjd.rtda.heap.cons.MethodRef;
import com.wjd.rtda.heap.member.Method;

/**
 * 执行静态方法
 * @since 2022/2/7
 */
public class InvokeStatic extends InvokeMethod {

    @Override
    public void execute(Frame frame) {
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        MethodRef methodRef = (MethodRef) cp.getConstant(index);
        Method method = methodRef.resolvedMethod();

        // 类初始化
        Class methodClass = method.getClazz();
        if (!methodClass.isInitStarted()) {
            frame.revertNextPc();
            InitClass.initClass(frame.getThread(), methodClass);
            return;
        }

        if (!method.isStatic()) {
            throw new IncompatibleClassChangeError("Invoke static method: " + method.getName());
        }
        invokeMethod(frame, method);
    }
}
