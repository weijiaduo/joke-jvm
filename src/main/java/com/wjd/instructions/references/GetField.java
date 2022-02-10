package com.wjd.instructions.references;

import com.wjd.instructions.base.Index16Instruction;
import com.wjd.rtda.Frame;
import com.wjd.rtda.OperandStack;
import com.wjd.rtda.Slot;
import com.wjd.rtda.heap.ConstantPool;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.heap.cons.FieldRef;
import com.wjd.rtda.heap.member.Field;

/**
 * @since 2022/2/1
 */
public class GetField extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        FieldRef fieldRef = (FieldRef) cp.getConstant(index);
        Field field = fieldRef.resolvedField();
        if (field.isStatic()) {
            throw new IncompatibleClassChangeError("putfield field: " + field.getName());
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
                HeapObject ref = stack.popRef();
                if (ref == null) {
                    throw new NullPointerException("putfield Field: " + field.getName());
                }
                Slot[] slots = ref.getFields();
                stack.pushInt(Slot.getInt(slots[slotId]));
                break;
            }
            case 'F':
            {
                HeapObject ref = stack.popRef();
                if (ref == null) {
                    throw new NullPointerException("putfield Field: " + field.getName());
                }
                Slot[] slots = ref.getFields();
                stack.pushFloat(Slot.getFloat(slots[slotId]));
                break;
            }
            case 'J':
            {
                HeapObject ref = stack.popRef();
                if (ref == null) {
                    throw new NullPointerException("putfield Field: " + field.getName());
                }
                Slot[] slots = ref.getFields();
                stack.pushLong(Slot.getLong(slots[slotId], slots[slotId + 1]));
                break;
            }
            case 'D':
            {
                HeapObject ref = stack.popRef();
                if (ref == null) {
                    throw new NullPointerException("putfield Field: " + field.getName());
                }
                Slot[] slots = ref.getFields();
                stack.pushDouble(Slot.getDouble(slots[slotId], slots[slotId + 1]));
                break;
            }
            case 'L':
            case '[':
            {
                HeapObject ref = stack.popRef();
                if (ref == null) {
                    throw new NullPointerException("putfield Field: " + field.getName());
                }
                Slot[] slots = ref.getFields();
                stack.pushRef(slots[slotId].getRef());
                break;
            }
            default:
        }
    }
}
