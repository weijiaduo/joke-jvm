package com.wjd.instructions.references;

import com.wjd.rtda.Frame;
import com.wjd.rtda.OperandStack;
import com.wjd.rtda.heap.Class;
import com.wjd.rtda.heap.ConstantPool;
import com.wjd.rtda.heap.Object;
import com.wjd.rtda.heap.cons.MethodRef;
import com.wjd.rtda.heap.member.Method;

/**
 * 执行多态方法（运行时确定实际执行的方法）
 * @since 2022/2/2
 */
public class InvokeVirtual extends InvokeMethod {

    @Override
    public void execute(Frame frame) {
        Class currentClass = frame.getMethod().getClazz();
        ConstantPool cp = currentClass.getConstantPool();
        MethodRef methodRef = (MethodRef) cp.getConstant(index);
        Method resolvedMethod = methodRef.resolvedMethod();

        if (resolvedMethod.isStatic()) {
            throw new IncompatibleClassChangeError("Invoke special method: " + resolvedMethod.getName());
        }

        // 调用方法的this对象
        Object ref = frame.getOperandStack().getRefFromTop(resolvedMethod.getParamSlotCount());
        if (ref == null) {
            // 临时方法
            if ("println".equals(methodRef.getName())) {
                println(frame, methodRef);
                return;
            }
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

        // 方法的多态性，运行时确定实际执行的方法
        Method methodToBeInvoked = MethodRef.lookupMethodInClass(ref.getClazz(),
                methodRef.getName(),
                methodRef.getDescriptor());

        // 未实现的抽象方法验证
        if (methodToBeInvoked == null || methodToBeInvoked.isAbstract()) {
            throw new AbstractMethodError("Invoke special method: " + resolvedMethod.getName());
        }

        invokeMethod(frame, methodToBeInvoked);
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
            default:
                System.out.println("println: " + methodRef.getDescriptor());
        }
        stack.popRef();
    }
}
