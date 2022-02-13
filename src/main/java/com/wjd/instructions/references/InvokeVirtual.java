package com.wjd.instructions.references;

import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.stack.OperandStack;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.ConstantPool;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.StringPool;
import com.wjd.rtda.meta.cons.MethodRef;
import com.wjd.rtda.meta.MethodMeta;

/**
 * 执行多态方法（运行时确定实际执行的方法）
 * @since 2022/2/2
 */
public class InvokeVirtual extends InvokeMethod {

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
            // FIXME: 临时方法
            if ("println".equals(methodRef.getName())) {
                println(frame, methodRef);
                return;
            }
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

        // 方法的多态性，运行时确定实际执行的方法
        MethodMeta methodMetaToBeInvoked = MethodRef.lookupMethodInClass(ref.getClazz(),
                methodRef.getName(),
                methodRef.getDescriptor());

        // 未实现的抽象方法验证
        if (methodMetaToBeInvoked == null || methodMetaToBeInvoked.isAbstract()) {
            throw new AbstractMethodError("Invoke special method: " + resolvedMethodMeta.getName());
        }

        invokeMethod(frame, methodMetaToBeInvoked);
    }

    private void println(Frame frame, MethodRef methodRef) {
        OperandStack stack = frame.getOperandStack();
        switch (methodRef.getDescriptor()) {
            case "(Z)V":
                System.out.println(stack.popInt() != 0);
                break;
            case "(C)V":
            case "(B)V":
            case "(S)V":
            case "(I)V":
                System.out.println(stack.popInt());
                break;
            case "(J)V":
                System.out.println(stack.popLong());
                break;
            case "(F)V":
                System.out.println(stack.popFloat());
                break;
            case "(D)V":
                System.out.println(stack.popDouble());
                break;
            case "(Ljava/lang/String;)V":
                HeapObject stringObj = stack.popRef();
                System.out.println(StringPool.getRawString(stringObj));
                break;
            default:
                System.out.println("println: " + methodRef.getDescriptor());
        }
        stack.popRef();
    }
}
