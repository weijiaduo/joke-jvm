package com.wjd.instructions.references;

import com.wjd.classfile.type.Uint8;
import com.wjd.instructions.base.ByteCodeReader;
import com.wjd.instructions.base.Index16Instruction;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.meta.ConstantPool;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.cons.InterfaceMethodRef;
import com.wjd.rtda.meta.cons.MethodRef;
import com.wjd.rtda.meta.MethodMeta;

/**
 * 执行接口方法
 * @since 2022/2/7
 */
public class InvokeInterface extends Index16Instruction {

    /** 参数个数 */
    private Uint8 count;
    /** 零，虚拟机兼容用 */
    private Uint8 zero;

    @Override
    public void execute(Frame frame) {
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        InterfaceMethodRef methodRef = (InterfaceMethodRef) cp.getConstant(index);
        MethodMeta resolvedMethodMeta = methodRef.resolvedInterfaceMethod();

        if (resolvedMethodMeta.isStatic() || resolvedMethodMeta.isPrivate()) {
            throw new IncompatibleClassChangeError("Invoke interface method: " + resolvedMethodMeta.getName());
        }

        // 调用方法的this对象
        HeapObject ref = frame.getOperandStack().getRefFromTop(resolvedMethodMeta.getParamSlotCount());
        if (ref == null) {
            throw new NullPointerException("Invoke interface method: " + resolvedMethodMeta.getName());
        }

        if (!ref.getClazz().isImplements(methodRef.resolvedClass())) {
            throw new IncompatibleClassChangeError("Invoke interface method: " + resolvedMethodMeta.getName());
        }

        // 方法的多态性，运行时确定实际执行的方法
        MethodMeta methodMetaToBeInvoked = MethodRef.lookupMethod(ref.getClazz(),
                methodRef.getName(), methodRef.getDescriptor());

        // 未实现的抽象方法验证
        if (methodMetaToBeInvoked == null || methodMetaToBeInvoked.isAbstract()) {
            throw new AbstractMethodError("Invoke interface method: " + resolvedMethodMeta.getName());
        }

        frame.getThread().invokeMethod(methodMetaToBeInvoked);
    }

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        source = reader.readUint16();
        index = source.value();
        count = reader.readUint8();
        zero = reader.readUint8();
    }
}
