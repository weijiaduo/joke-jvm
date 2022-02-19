package com.wjd.instructions.references;

import com.wjd.instructions.base.Index16Instruction;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.stack.OperandStack;
import com.wjd.rtda.Slot;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.ConstantPool;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.cons.FieldRef;
import com.wjd.rtda.meta.FieldMeta;
import com.wjd.rtda.meta.MethodMeta;

/**
 * @since 2022/2/1
 */
public class PutField extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        MethodMeta currentMethod = frame.getMethod();
        ClassMeta currentClazz = currentMethod.getClazz();
        ConstantPool cp = currentClazz.getConstantPool();
        FieldRef fieldRef = (FieldRef) cp.getConstant(index);
        FieldMeta field = fieldRef.resolvedField();
        ClassMeta fieldClazz = field.getClazz();

        if (field.isStatic()) {
            throw new IncompatibleClassChangeError("putfield field: " + field.getName());
        }
        if (field.isFinal()) {
            // final 字段的初始化必须在构造函数中做
            if (currentClazz != fieldClazz || !"<init>".equals(currentMethod.getName())) {
                throw new IllegalAccessError("putfield field: " + field.getName());
            }
        }

        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
        OperandStack stack = frame.getOpStack();
        char d = descriptor.charAt(0);
        switch (d) {
            case 'Z':
            case 'B':
            case 'C':
            case 'S':
            case 'I':
            {
                int val = stack.popInt();
                HeapObject ref = stack.popRef();
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
                HeapObject ref = stack.popRef();
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
                HeapObject ref = stack.popRef();
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
                HeapObject ref = stack.popRef();
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
                HeapObject val = stack.popRef();
                HeapObject ref = stack.popRef();
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
