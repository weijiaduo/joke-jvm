package com.wjd.instructions.references;

import com.wjd.instructions.base.Index16Instruction;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.stack.OperandStack;
import com.wjd.rtda.Slot;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.ConstantPool;
import com.wjd.rtda.meta.cons.FieldRef;
import com.wjd.rtda.meta.FieldMeta;

/**
 * @since 2022/2/1
 */
public class GetStatic extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        FieldRef fieldRef = (FieldRef) cp.getConstant(index);
        FieldMeta field = fieldRef.resolvedField();
        ClassMeta fieldClazz = field.getClazz();

        // 类初始化
        if (!fieldClazz.isInitStarted()) {
            frame.revertNextPc();
            InitClass.initClass(frame.getThread(), fieldClazz);
            return;
        }

        if (!field.isStatic()) {
            throw new IncompatibleClassChangeError("putstatic field: " + field.getName());
        }

        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
        Slot[] slots = fieldClazz.getStaticVars();
        OperandStack stack = frame.getOpStack();
        char d = descriptor.charAt(0);
        switch (d) {
            case 'Z':
            case 'B':
            case 'C':
            case 'S':
            case 'I':
                stack.pushInt(Slot.getInt(slots[slotId]));
                break;
            case 'F':
                stack.pushFloat(Slot.getFloat(slots[slotId]));
                break;
            case 'J':
                stack.pushLong(Slot.getLong(slots[slotId], slots[slotId + 1]));
                break;
            case 'D':
                stack.pushDouble(Slot.getDouble(slots[slotId], slots[slotId + 1]));
                break;
            case 'L':
            case '[':
                stack.pushRef(slots[slotId].getRef());
                break;
            default:
        }
    }
}
