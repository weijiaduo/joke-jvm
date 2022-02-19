package com.wjd.instructions.references;

import com.wjd.instructions.base.Index16Instruction;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.stack.OperandStack;
import com.wjd.rtda.Slot;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.ConstantPool;
import com.wjd.rtda.meta.cons.FieldRef;
import com.wjd.rtda.meta.FieldMeta;
import com.wjd.rtda.meta.MethodMeta;

/**
 * @since 2022/2/1
 */
public class PutStatic extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        MethodMeta currentMethod = frame.getMethod();
        ClassMeta currentClazz = currentMethod.getClazz();
        ConstantPool cp = currentClazz.getConstantPool();
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
        if (field.isFinal()) {
            if (currentClazz != fieldClazz || !"<clinit>".equals(currentMethod.getName())) {
                throw new IllegalAccessError("putstatic field: " + field.getName());
            }
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
