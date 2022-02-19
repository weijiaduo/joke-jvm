package com.wjd.instructions.constants;

import com.wjd.instructions.base.Index8Instruction;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.stack.OperandStack;
import com.wjd.rtda.meta.ConstantPool;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.StringPool;
import com.wjd.rtda.meta.cons.*;

/**
 * @since 2022/2/2
 */
public class IDC extends Index8Instruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOpStack();
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        Constant constant = cp.getConstant(index);
        if(constant instanceof IntegerConstant) {
            stack.pushInt(((IntegerConstant) constant).value());
        } else if (constant instanceof FloatConstant) {
            stack.pushFloat(((FloatConstant) constant).value());
        } else if (constant instanceof StringConstant) {
            String string = ((StringConstant) constant).value();
            HeapObject stringObj = StringPool.getStringObj(frame.getMethod().getClazz().getLoader(), string);
            stack.pushRef(stringObj);
        } else if (constant instanceof ClassRef) {
            HeapObject jClassObj = ((ClassRef) constant).resolvedClass().getjClass();
            stack.pushRef(jClassObj);
        } else {
            System.out.println("Unsupported Type: " + constant);
        }
    }
}
