package com.wjd.instructions.references;

import com.wjd.instructions.base.Index16Instruction;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.ConstantPool;
import com.wjd.rtda.meta.MethodMeta;
import com.wjd.rtda.meta.cons.MethodRef;
import com.wjd.rtda.stack.Frame;
import com.wjd.util.MethodHelper;

/**
 * 执行多态方法（运行时确定实际执行的方法）
 * @since 2022/2/2
 */
public class InvokeVirtual extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        ClassMeta currentClassMeta = frame.getMethod().getClassMeta();
        ConstantPool cp = currentClassMeta.getConstantPool();
        MethodRef methodRef = (MethodRef) cp.getConstant(index);
        MethodMeta resolvedMethod = methodRef.resolvedMethod();

        if (resolvedMethod.isStatic()) {
            throw new IncompatibleClassChangeError("Invoke special method: " + resolvedMethod.getName());
        }

        // 调用方法的this对象
        HeapObject ref = frame.getOpStack().getRefFromTop(resolvedMethod.getParamSlotCount());
        if (ref == null) {
            throw new NullPointerException("Invoke special method: " + resolvedMethod.getName());
        }

        // 调用方法是protected时的权限验证
        if (resolvedMethod.isProtected() &&
                resolvedMethod.getClassMeta().isSuperClassOf(currentClassMeta) &&
                !resolvedMethod.getClassMeta().getPackageName().equals(currentClassMeta.getPackageName()) &&
                ref.getClassMeta() != currentClassMeta &&
                !ref.getClassMeta().isSubClassOf(currentClassMeta) &&
                !ref.getClassMeta().isArray()) {
            throw new IllegalAccessError("Invoke special method: " + resolvedMethod.getName());
        }

        // 方法的多态性，运行时确定实际执行的方法
        MethodMeta methodToBeInvoked = MethodHelper.lookupMethodInClass(ref.getClassMeta(),
                methodRef.getName(),
                methodRef.getDescriptor());

        // 未实现的抽象方法验证
        if (methodToBeInvoked == null || methodToBeInvoked.isAbstract()) {
            throw new AbstractMethodError("Invoke special method: " + resolvedMethod.getName());
        }

        frame.getThread().invokeMethod(methodToBeInvoked);
    }

}
