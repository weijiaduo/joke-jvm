package com.wjd.instructions.references;

import com.wjd.instructions.base.Index16Instruction;
import com.wjd.rtda.Frame;
import com.wjd.rtda.OperandStack;
import com.wjd.rtda.heap.ConstantPool;
import com.wjd.rtda.heap.cons.MethodRef;

/**
 * @since 2022/2/2
 */
public class InvokeVirtual extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        MethodRef methodRef = (MethodRef) cp.getConstant(index);
        if ("println".equals(methodRef.getName())) {
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
}
