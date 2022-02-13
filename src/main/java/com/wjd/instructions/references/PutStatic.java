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
        MethodMeta currentMethodMeta = frame.getMethod();
        ClassMeta currentClassMeta = currentMethodMeta.getClazz();
        ConstantPool cp = currentClassMeta.getConstantPool();
        FieldRef fieldRef = (FieldRef) cp.getConstant(index);
        FieldMeta fieldMeta = fieldRef.resolvedField();
        ClassMeta fieldClassMeta = fieldMeta.getClazz();

        // 类初始化
        if (!fieldClassMeta.isInitStarted()) {
            frame.revertNextPc();
            InitClass.initClass(frame.getThread(), fieldClassMeta);
            return;
        }

        if (!fieldMeta.isStatic()) {
            throw new IncompatibleClassChangeError("putstatic field: " + fieldMeta.getName());
        }
        if (fieldMeta.isFinal()) {
            if (currentClassMeta != fieldClassMeta || !"<clinit>".equals(currentMethodMeta.getName())) {
                throw new IllegalAccessError("putstatic field: " + fieldMeta.getName());
            }
        }

        String descriptor = fieldMeta.getDescriptor();
        int slotId = fieldMeta.getSlotId();
        Slot[] slots = fieldClassMeta.getStaticVars();
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
