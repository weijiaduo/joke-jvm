package com.wjd.instructions.constants;

import com.wjd.instructions.base.Index16Instruction;
import com.wjd.rtda.Frame;
import com.wjd.rtda.OperandStack;
import com.wjd.rtda.heap.ConstantPool;
import com.wjd.rtda.heap.cons.Constant;
import com.wjd.rtda.heap.cons.FloatConstant;
import com.wjd.rtda.heap.cons.IntegerConstant;

/**
 * @since 2022/2/2
 */
public class IDCW extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        Constant constant = cp.getConstant(index);
        if(constant instanceof IntegerConstant) {
            stack.pushInt(((IntegerConstant) constant).value());
        } else if (constant instanceof FloatConstant) {
            stack.pushFloat(((FloatConstant) constant).value());
        } else {
            System.out.println("Unsupported Type: " + constant);
        }
    }
}
