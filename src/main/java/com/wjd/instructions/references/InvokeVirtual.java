package com.wjd.instructions.references;

import com.wjd.instructions.base.Index16Instruction;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.ConstantPool;
import com.wjd.rtda.meta.MethodMeta;
import com.wjd.rtda.meta.cons.MethodRef;
import com.wjd.rtda.stack.Frame;

/**
 * 执行多态方法（运行时确定实际执行的方法）
 * @since 2022/2/2
 */
public class InvokeVirtual extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        ClassMeta currentClassMeta = frame.getMethod().getClazz();
        ConstantPool cp = currentClassMeta.getConstantPool();
        MethodRef methodRef = (MethodRef) cp.getConstant(index);
        MethodMeta resolvedMethodMeta = methodRef.resolvedMethod();

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
                !ref.getClazz().isSubClassOf(currentClassMeta) &&
                !ref.getClazz().isArray()) {
            throw new IllegalAccessError("Invoke special method: " + resolvedMethodMeta.getName());
        }

        // 方法的多态性，运行时确定实际执行的方法
        MethodMeta methodMetaToBeInvoked = MethodRef.lookupMethodInClass(ref.getClazz(),
                methodRef.getName(),
                methodRef.getDescriptor());

        // 未实现的抽象方法验证
        if (methodMetaToBeInvoked == null || methodMetaToBeInvoked.isAbstract()) {
            throw new AbstractMethodError("Invoke special method: " + resolvedMethodMeta.getName());
        }

        frame.getThread().invokeMethod(methodMetaToBeInvoked);
    }

}
