package com.wjd.instructions.reserved;

import com.wjd.instructions.constants.NoOperandsInstruction;
import com.wjd.naive.NativeMethod;
import com.wjd.naive.NativeRegistry;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.meta.MethodMeta;

/**
 * 本地方法命令
 * @since 2022/2/12
 */
public class InvokeNative extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        MethodMeta method = frame.getMethod();
        String className = method.getClazz().getName();
        String methodName = method.getName();
        String methodDescriptor = method.getDescriptor();
        NativeMethod nativeMethod = NativeRegistry.findNativeMethod(className, methodName, methodDescriptor);
        if (nativeMethod == null) {
            String methodInfo = className + "." + methodName + methodDescriptor;
            throw new UnsatisfiedLinkError(methodInfo);
        }
        try {
            nativeMethod.execute(frame);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
