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
 * 执行实例方法（构造函数、私有方法、super调用方法）
 * @since 2022/2/2
 */
public class InvokeSpecial extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        ClassMeta currentClassMeta = frame.getMethod().getClassMeta();
        ConstantPool cp = currentClassMeta.getConstantPool();
        MethodRef methodRef = (MethodRef) cp.getConstant(index);
        ClassMeta resolvedClassMeta = methodRef.resolvedClass();
        MethodMeta resolvedMethod = methodRef.resolvedMethod();

        // 构造方法验证
        if ("<init>".equals(resolvedMethod.getName()) && resolvedMethod.getClassMeta() != resolvedClassMeta) {
            throw new NoSuchMethodError("Invoke special method: " + resolvedMethod.getName());
        }
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
                !ref.getClassMeta().isSubClassOf(currentClassMeta)) {
            throw new IllegalAccessError("Invoke special method: " + resolvedMethod.getName());
        }

        MethodMeta methodToBeInvoked = resolvedMethod;

        // 调用super关键字
        if (currentClassMeta.isSuper() &&
                resolvedClassMeta.isSuperClassOf(currentClassMeta) &&
                !"<init>".equals(resolvedMethod.getName())) {
            methodToBeInvoked = MethodHelper.lookupMethodInClass(currentClassMeta.getSuperClass(),
                    methodRef.getName(),
                    methodRef.getDescriptor());
        }

        // 未实现的抽象方法验证
        if (methodToBeInvoked == null || methodToBeInvoked.isAbstract()) {
            throw new AbstractMethodError("Invoke special method: " + resolvedMethod.getName());
        }

        frame.getThread().invokeMethod(methodToBeInvoked);
    }
}
