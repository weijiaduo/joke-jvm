package com.wjd.instructions.references;

import com.wjd.instructions.base.Index16Instruction;
import com.wjd.rtda.Frame;
import com.wjd.rtda.OperandStack;
import com.wjd.rtda.Slot;
import com.wjd.rtda.heap.Class;
import com.wjd.rtda.heap.ConstantPool;
import com.wjd.rtda.heap.cons.FieldRef;
import com.wjd.rtda.heap.member.Field;
import com.wjd.rtda.heap.member.Method;

/**
 * @since 2022/2/1
 */
public class PutStatic extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        Method currentMethod = frame.getMethod();
        Class currentClass = currentMethod.getClazz();
        ConstantPool cp = currentClass.getConstantPool();
        FieldRef fieldRef = (FieldRef) cp.getConstant(index);
        Field field = fieldRef.resolvedField();
        Class fieldClass = field.getClazz();

        // 类初始化
        if (!fieldClass.isInitStarted()) {
            frame.revertNextPc();
            InitClass.initClass(frame.getThread(), fieldClass);
            return;
        }

        if (!field.isStatic()) {
            throw new IncompatibleClassChangeError("putstatic field: " + field.getName());
        }
        if (field.isFinal()) {
            if (currentClass != fieldClass || !"<clinit>".equals(currentMethod.getName())) {
                throw new IllegalAccessError("putstatic field: " + field.getName());
            }
        }

        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
        Slot[] slots = fieldClass.getStaticVars();
        OperandStack stack = frame.getOperandStack();
        char d = descriptor.charAt(0);
        switch (d) {
            case 'Z':
            case 'B':
            case 'C':
            case 'S':
            case 'I':
                Slot.setInt(slots[slotId], stack.popInt());
                break;
            case 'F':
                Slot.setFloat(slots[slotId], stack.popFloat());
                break;
            case 'J':
                Slot.setLong(slots[slotId], slots[slotId + 1], stack.popLong());
                break;
            case 'D':
                Slot.setDouble(slots[slotId], slots[slotId + 1], stack.popDouble());
                break;
            case 'L':
            case '[':
                slots[slotId].setRef(stack.popRef());
                break;
            default:
        }
    }
}
