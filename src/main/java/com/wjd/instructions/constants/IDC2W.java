package com.wjd.instructions.constants;

import com.wjd.instructions.base.Index16Instruction;
import com.wjd.rtda.meta.ConstantPool;
import com.wjd.rtda.meta.cons.Constant;
import com.wjd.rtda.meta.cons.DoubleConstant;
import com.wjd.rtda.meta.cons.LongConstant;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.stack.OperandStack;

/**
 * @since 2022/2/2
 */
public class IDC2W extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOpStack();
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        Constant constant = cp.getConstant(index);
        if (constant instanceof LongConstant) {
            stack.pushLong(((LongConstant) constant).value());
        } else if (constant instanceof DoubleConstant) {
            stack.pushDouble(((DoubleConstant) constant).value());
        } else {
            throw new ClassFormatError("Constant: " + constant);
        }
    }
}
