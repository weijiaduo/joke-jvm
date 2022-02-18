package com.wjd.instructions.references;

import com.wjd.instructions.base.Index16Instruction;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.ConstantPool;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.cons.MethodRef;
import com.wjd.rtda.meta.MethodMeta;

/**
 * 执行实例方法（构造函数、私有方法、super调用方法）
 * @since 2022/2/2
 */
public class InvokeSpecial extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        ClassMeta currentClassMeta = frame.getMethod().getClazz();
        ConstantPool cp = currentClassMeta.getConstantPool();
        MethodRef methodRef = (MethodRef) cp.getConstant(index);
        ClassMeta resolvedClassMeta = methodRef.resolvedClass();
        MethodMeta resolvedMethodMeta = methodRef.resolvedMethod();

        // 构造方法验证
        if ("<init>".equals(resolvedMethodMeta.getName()) && resolvedMethodMeta.getClazz() != resolvedClassMeta) {
            throw new NoSuchMethodError("Invoke special method: " + resolvedMethodMeta.getName());
        }
        if (resolvedMethodMeta.isStatic()) {
            throw new IncompatibleClassChangeError("Invoke special method: " + resolvedMethodMeta.getName());
        }

        // 调用方法的this对象
        HeapObject ref = frame.getOperandStack().getRefFromTop(resolvedMethodMeta.getParamSlotCount());
        if (ref == null) {
            throw new NullPointerException("Invoke special method: " + resolvedMethodMeta.getName());
        }

        // 调用方法是protected时的权限验证
        if (resolvedMethodMeta.isProtected() &&
                resolvedMethodMeta.getClazz().isSuperClassOf(currentClassMeta) &&
                !resolvedMethodMeta.getClazz().getPackageName().equals(currentClassMeta.getPackageName()) &&
                ref.getClazz() != currentClassMeta &&
                !ref.getClazz().isSubClassOf(currentClassMeta)) {
            throw new IllegalAccessError("Invoke special method: " + resolvedMethodMeta.getName());
        }

        MethodMeta methodMetaToBeInvoked = resolvedMethodMeta;

        // 调用super关键字
        if (currentClassMeta.isSuper() &&
                resolvedClassMeta.isSuperClassOf(currentClassMeta) &&
                !"<init>".equals(resolvedMethodMeta.getName())) {
            methodMetaToBeInvoked = MethodRef.lookupMethodInClass(currentClassMeta.getSuperClass(),
                    methodRef.getName(),
                    methodRef.getDescriptor());
        }

        // 未实现的抽象方法验证
        if (methodMetaToBeInvoked == null || methodMetaToBeInvoked.isAbstract()) {
            throw new AbstractMethodError("Invoke special method: " + resolvedMethodMeta.getName());
        }

        frame.getThread().invokeMethod(methodMetaToBeInvoked);
    }
}
