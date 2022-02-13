package com.wjd.instructions.references;

import com.wjd.instructions.base.Index16Instruction;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.stack.OperandStack;
import com.wjd.rtda.Slot;
import com.wjd.rtda.meta.ConstantPool;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.cons.FieldRef;
import com.wjd.rtda.meta.FieldMeta;

/**
 * @since 2022/2/1
 */
public class GetField extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        FieldRef fieldRef = (FieldRef) cp.getConstant(index);
        FieldMeta fieldMeta = fieldRef.resolvedField();
        if (fieldMeta.isStatic()) {
            throw new IncompatibleClassChangeError("getfield field: " + fieldMeta.getName());
        }

        String descriptor = fieldMeta.getDescriptor();
        int slotId = fieldMeta.getSlotId();
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
                    throw new NullPointerException("getfield Field: " + fieldMeta.getName());
                }
                Slot[] slots = ref.getFields();
                stack.pushInt(Slot.getInt(slots[slotId]));
                break;
            }
            case 'F':
            {
                HeapObject ref = stack.popRef();
                if (ref == null) {
                    throw new NullPointerException("getfield Field: " + fieldMeta.getName());
                }
                Slot[] slots = ref.getFields();
                stack.pushFloat(Slot.getFloat(slots[slotId]));
                break;
            }
            case 'J':
            {
                HeapObject ref = stack.popRef();
                if (ref == null) {
                    throw new NullPointerException("getfield Field: " + fieldMeta.getName());
                }
                Slot[] slots = ref.getFields();
                stack.pushLong(Slot.getLong(slots[slotId], slots[slotId + 1]));
                break;
            }
            case 'D':
            {
                HeapObject ref = stack.popRef();
                if (ref == null) {
                    throw new NullPointerException("getfield Field: " + fieldMeta.getName());
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
                    throw new NullPointerException("getfield Field: " + fieldMeta.getName());
                }
                Slot[] slots = ref.getFields();
                stack.pushRef(slots[slotId].getRef());
                break;
            }
            default:
        }
    }
}
