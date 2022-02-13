package com.wjd.rtda.meta.cons;

import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.ConstantPool;

/**
 * 符号引用
 * @since 2022/1/30
 */
public abstract class SymbolRef extends Constant {

    protected ConstantPool constantPool;
    protected String className;
    protected ClassMeta clazz;

    public ClassMeta resolvedClass() {
        if (clazz == null) {
            resolveClassRef();
        }
        return clazz;
    }

    private void resolveClassRef() {
        ClassMeta currentClassMeta = constantPool.getClazz();
        ClassMeta refClassMeta = currentClassMeta.getLoader().loadClass(className);
        if (!refClassMeta.isAccessibleTo(currentClassMeta)) {
            throw new IllegalAccessError("Class " + currentClassMeta.getName()
                    + " can not access Class " + refClassMeta.getName());
        }
        clazz = refClassMeta;
    }

}
