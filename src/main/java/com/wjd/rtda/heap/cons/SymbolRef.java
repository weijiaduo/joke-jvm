package com.wjd.rtda.heap.cons;

import com.wjd.rtda.heap.Class;
import com.wjd.rtda.heap.ConstantPool;

/**
 * 符号引用
 * @since 2022/1/30
 */
public abstract class SymbolRef extends Constant {

    protected ConstantPool constantPool;
    protected String className;
    protected Class clazz;

    public Class resolvedClass() {
        if (clazz == null) {
            resolveClassRef();
        }
        return clazz;
    }

    private void resolveClassRef() {
        Class currentClass = constantPool.getClazz();
        Class refClass = currentClass.getLoader().loadClass(className);
        if (!refClass.isAccessibleTo(currentClass)) {
            throw new IllegalAccessError("Class " + currentClass.getName()
                    + " can not access Class " + refClass.getName());
        }
        clazz = refClass;
    }

}
