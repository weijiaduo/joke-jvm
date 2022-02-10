package com.wjd.instructions.references;

import com.wjd.rtda.Frame;
import com.wjd.rtda.heap.Class;
import com.wjd.rtda.heap.ConstantPool;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.heap.cons.MethodRef;
import com.wjd.rtda.heap.member.Method;

/**
 * 执行实例方法（构造函数、私有方法、super调用方法）
 * @since 2022/2/2
 */
public class InvokeSpecial extends InvokeMethod {

    @Override
    public void execute(Frame frame) {
        Class currentClass = frame.getMethod().getClazz();
        ConstantPool cp = currentClass.getConstantPool();
        MethodRef methodRef = (MethodRef) cp.getConstant(index);
        Class resolvedClass = methodRef.resolvedClass();
        Method resolvedMethod = methodRef.resolvedMethod();

        // 构造方法验证
        if ("<init>".equals(resolvedMethod.getName()) && resolvedMethod.getClazz() != resolvedClass) {
            throw new NoSuchMethodError("Invoke special method: " + resolvedMethod.getName());
        }
        if (resolvedMethod.isStatic()) {
            throw new IncompatibleClassChangeError("Invoke special method: " + resolvedMethod.getName());
        }

        // 调用方法的this对象
        HeapObject ref = frame.getOperandStack().getRefFromTop(resolvedMethod.getParamSlotCount());
        if (ref == null) {
            throw new NullPointerException("Invoke special method: " + resolvedMethod.getName());
        }

        // 调用方法是protected时的权限验证
        if (resolvedMethod.isProtected() &&
                resolvedMethod.getClazz().isSuperClassOf(currentClass) &&
                !resolvedMethod.getClazz().getPackageName().equals(currentClass.getPackageName()) &&
                ref.getClazz() != currentClass &&
                !ref.getClazz().isSubClassOf(currentClass)) {
            throw new IllegalAccessError("Invoke special method: " + resolvedMethod.getName());
        }

        Method methodToBeInvoked = resolvedMethod;

        // 调用super关键字
        if (currentClass.isSuper() &&
                resolvedClass.isSuperClassOf(currentClass) &&
                !"<init>".equals(resolvedMethod.getName())) {
            methodToBeInvoked = MethodRef.lookupMethodInClass(currentClass.getSuperClass(),
                    methodRef.getName(),
                    methodRef.getDescriptor());
        }

        // 未实现的抽象方法验证
        if (methodToBeInvoked == null || methodToBeInvoked.isAbstract()) {
            throw new AbstractMethodError("Invoke special method: " + resolvedMethod.getName());
        }

        invokeMethod(frame, methodToBeInvoked);
    }
}
