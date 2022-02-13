package com.wjd.instructions.constants;

import com.wjd.instructions.base.Index16Instruction;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.stack.OperandStack;
import com.wjd.rtda.meta.ConstantPool;
import com.wjd.rtda.meta.cons.Constant;
import com.wjd.rtda.meta.cons.FloatConstant;
import com.wjd.rtda.meta.cons.IntegerConstant;

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
