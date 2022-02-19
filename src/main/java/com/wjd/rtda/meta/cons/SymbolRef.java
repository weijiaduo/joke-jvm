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

    /**
     * 解析类符号引用
     */
    private void resolveClassRef() {
        ClassMeta currentClazz = constantPool.getClazz();
        ClassMeta refClazz = currentClazz.getLoader().loadClass(className);
        if (!refClazz.isAccessibleTo(currentClazz)) {
            throw new IllegalAccessError("Class " + currentClazz.getName()
                    + " can not access Class " + refClazz.getName());
        }
        clazz = refClazz;
    }

}
