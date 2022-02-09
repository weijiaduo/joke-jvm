package com.wjd.instructions.references;

import com.wjd.classfile.type.Uint8;
import com.wjd.instructions.base.ByteCodeReader;
import com.wjd.rtda.Frame;
import com.wjd.rtda.heap.ConstantPool;
import com.wjd.rtda.heap.Object;
import com.wjd.rtda.heap.cons.InterfaceMethodRef;
import com.wjd.rtda.heap.cons.MethodRef;
import com.wjd.rtda.heap.member.Method;

/**
 * 执行接口方法
 * @since 2022/2/7
 */
public class InvokeInterface extends InvokeMethod {

    /** 参数个数 */
    private Uint8 count;
    /** 零，虚拟机兼容用 */
    private Uint8 zero;

    @Override
    public void execute(Frame frame) {
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        InterfaceMethodRef methodRef = (InterfaceMethodRef) cp.getConstant(index);
        Method resolvedMethod = methodRef.resolvedInterfaceMethod();

        if (resolvedMethod.isStatic() || resolvedMethod.isPrivate()) {
            throw new IncompatibleClassChangeError("Invoke interface method: " + resolvedMethod.getName());
        }

        // 调用方法的this对象
        Object ref = frame.getOperandStack().getRefFromTop(resolvedMethod.getParamSlotCount());
        if (ref == null) {
            throw new NullPointerException("Invoke interface method: " + resolvedMethod.getName());
        }

        if (!ref.getClazz().isImplements(methodRef.resolvedClass())) {
            throw new IncompatibleClassChangeError("Invoke interface method: " + resolvedMethod.getName());
        }

        // 方法的多态性，运行时确定实际执行的方法
        Method methodToBeInvoked = MethodRef.lookupMethod(ref.getClazz(),
                methodRef.getName(), methodRef.getDescriptor());

        // 未实现的抽象方法验证
        if (methodToBeInvoked == null || methodToBeInvoked.isAbstract()) {
            throw new AbstractMethodError("Invoke interface method: " + resolvedMethod.getName());
        }

        invokeMethod(frame, methodToBeInvoked);
    }

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        source = reader.readUint16();
        index = source.value();
        count = reader.readUint8();
        zero = reader.readUint8();
    }
}
