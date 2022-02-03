package com.wjd.instructions.constants;

import com.wjd.instructions.base.Index8Instruction;
import com.wjd.rtda.Frame;
import com.wjd.rtda.OperandStack;
import com.wjd.rtda.heap.ConstantPool;
import com.wjd.rtda.heap.cons.*;

/**
 * @since 2022/2/2
 */
public class IDC2W extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        Constant constant = cp.getConstant(index);
        if(constant instanceof LongConstant) {
            stack.pushLong(((LongConstant) constant).value());
        } else if (constant instanceof DoubleConstant) {
            stack.pushDouble(((DoubleConstant) constant).value());
        } else {
            throw new ClassFormatError("Constant: " + constant);
        }
    }
}
