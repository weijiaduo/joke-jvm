package com.wjd.instructions.references;

import com.wjd.instructions.base.Index16Instruction;
import com.wjd.rtda.Frame;
import com.wjd.rtda.OperandStack;
import com.wjd.rtda.Slot;
import com.wjd.rtda.heap.Class;
import com.wjd.rtda.heap.ConstantPool;
import com.wjd.rtda.heap.Object;
import com.wjd.rtda.heap.cons.FieldRef;
import com.wjd.rtda.heap.member.Field;
import com.wjd.rtda.heap.member.Method;

/**
 * @since 2022/2/1
 */
public class PutField extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        Method currentMethod = frame.getMethod();
        Class currentClass = currentMethod.getClazz();
        ConstantPool cp = currentClass.getConstantPool();
        FieldRef fieldRef = (FieldRef) cp.getConstant(index);
        Field field = fieldRef.resolvedField();
        Class fieldClass = field.getClazz();
        if (field.isStatic()) {
            throw new IncompatibleClassChangeError("putfield field: " + field.getName());
        }
        if (field.isFinal()) {
            if (currentClass != fieldClass || !"<cinit>".equals(currentMethod.getName())) {
                throw new IllegalAccessError("putfield field: " + field.getName());
            }
        }

        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
        OperandStack stack = frame.getOperandStack();
        char d = descriptor.charAt(0);
        switch (d) {
            case 'Z':
            case 'B':
            case 'C':
            case 'S':
            case 'I':
            {
                int val = stack.popInt();
                Object ref = stack.popRef();
                if (ref == null) {
                    throw new NullPointerException("putfield Field: " + field.getName());
                }
                Slot[] slots = ref.getFields();
                Slot.setInt(slots[slotId], val);
                break;
            }
            case 'F':
            {
                float val = stack.popFloat();
                Object ref = stack.popRef();
                if (ref == null) {
                    throw new NullPointerException("putfield Field: " + field.getName());
                }
                Slot[] slots = ref.getFields();
                Slot.setFloat(slots[slotId], val);
                break;
            }
            case 'J':
            {
                long val = stack.popLong();
                Object ref = stack.popRef();
                if (ref == null) {
                    throw new NullPointerException("putfield Field: " + field.getName());
                }
                Slot[] slots = ref.getFields();
                Slot.setLong(slots[slotId], slots[slotId + 1], val);
                break;
            }
            case 'D':
            {
                double val = stack.popDouble();
                Object ref = stack.popRef();
                if (ref == null) {
                    throw new NullPointerException("putfield Field: " + field.getName());
                }
                Slot[] slots = ref.getFields();
                Slot.setDouble(slots[slotId], slots[slotId + 1], val);
                break;
            }
            case 'L':
            case '[':
            {
                Object val = stack.popRef();
                Object ref = stack.popRef();
                if (ref == null) {
                    throw new NullPointerException("putfield Field: " + field.getName());
                }
                Slot[] slots = ref.getFields();
                slots[slotId].setRef(val);
                break;
            }
            default:
        }
    }
}
