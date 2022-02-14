package com.wjd.rtda.meta;

import com.wjd.classfile.attr.ExceptionInfoTable;
import com.wjd.classfile.attr.ExceptionTableEntry;
import com.wjd.rtda.meta.cons.ClassRef;

/**
 * @since 2022/2/14
 */
public class ExceptionTable {

    private ExceptionHandler[] exceptionHandlers;

    public ExceptionTable(ExceptionHandler[] exceptionHandlers) {
        this.exceptionHandlers = exceptionHandlers;
    }

    public static ExceptionTable newExceptionTable(ExceptionInfoTable exceptionInfoTable, ConstantPool cp) {
        ExceptionTableEntry[] exceptionEntries = exceptionInfoTable.getExceptionEntries();
        ExceptionHandler[] exceptionHandlers = new ExceptionHandler[exceptionEntries.length];
        for (int i = 0; i < exceptionHandlers.length; i++) {
            ExceptionTableEntry entry = exceptionEntries[i];
            exceptionHandlers[i] = new ExceptionHandler(entry.getStartPC().value(),
                    entry.getEndPC().value(),
                    entry.getHandlerPC().value(),
                    getCatchType(entry.getCatchPC().value(), cp));
        }
        return new ExceptionTable(exceptionHandlers);
    }

    /**
     * 捕获的异常类
     */
    private static ClassRef getCatchType(int catchType, ConstantPool cp) {
        if (catchType == 0) {
            // 0表示捕获所有异常
            return null;
        }
        return (ClassRef) cp.getConstant(catchType);
    }

    /**
     * 查找可以捕获异常的异常类
     * @param exClass 抛出异常的类
     * @param pc 抛出异常的pc地址
     * @return 可捕获异常的异常处理器
     */
    public ExceptionHandler findExceptionHandler(ClassMeta exClass, int pc) {
        for (ExceptionHandler handler : exceptionHandlers) {
            if (handler.getStartPC() <= pc && pc < handler.getEndPC()) {
                if (handler.getCatchType() == null) {
                    // 等于null表示捕获所有异常
                    return handler;
                }
                ClassMeta catchClass = handler.getCatchType().resolvedClass();
                if (catchClass == exClass || catchClass.isSuperClassOf(exClass)) {
                    // 捕获的异常是抛出异常的父类
                    return handler;
                }
            }
        }
        return null;
    }
}
