package com.wjd.instructions.references;

import com.wjd.classfile.type.Uint16;
import com.wjd.classfile.type.Uint8;
import com.wjd.instructions.base.ByteCodeReader;
import com.wjd.instructions.base.Instruction;
import com.wjd.rtda.Frame;
import com.wjd.rtda.OperandStack;
import com.wjd.rtda.heap.Class;
import com.wjd.rtda.heap.ClassLoader;
import com.wjd.rtda.heap.ConstantPool;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.heap.cons.ClassRef;

import java.util.Arrays;

/**
 * 创建多维数组对象
 * @since 2022/2/1
 */
public class MultiANewArray implements Instruction {

    private Uint16 index;
    private Uint8 dimension;

    @Override
    public void execute(Frame frame) {
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        ClassRef classRef = (ClassRef) cp.getConstant(index.value());
        Class arrayClass = classRef.resolvedClass();
        int[] counts = popAndCheckCounts(frame.getOperandStack(), dimension.value());
        HeapObject arrayObject = newMultiDimensionalArray(counts, arrayClass);
        frame.getOperandStack().pushRef(arrayObject);
    }

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        index = reader.readUint16();
        dimension = reader.readUint8();
    }

    private int[] popAndCheckCounts(OperandStack stack, int dimension) {
        int[] counts = new int[dimension];
        for(int i = dimension - 1; i>= 0; i--) {
            int count = stack.popInt();
            if (count < 0) {
                throw new NegativeArraySizeException("MultiANewArray count: " + count);
            }
            counts[i] = count;
        }
        return counts;
    }

    private HeapObject newMultiDimensionalArray(int[] counts, Class arrayClass) {
        int count = counts[0];
        HeapObject arrayObject = arrayClass.newArray(count);
        if (counts.length > 1) {
            int[] newCounts = Arrays.copyOfRange(counts, 1, counts.length);
            HeapObject[] refs = arrayObject.getRefs();
            for (int i = 0; i < refs.length; i++) {
                refs[i] = newMultiDimensionalArray(newCounts, arrayClass.getComponentClass());
            }
        }
        return arrayObject;
    }

}
