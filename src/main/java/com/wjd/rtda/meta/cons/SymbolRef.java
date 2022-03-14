package com.wjd.rtda.meta.cons;

import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.ConstantPool;

/**
 * 符号引用
 * @since 2022/1/30
 */
public abstract class SymbolRef extends Constant {

    /** 常量池 */
    protected ConstantPool constantPool;
    /** 所属类完全限定名 */
    protected String className;
    /** 所属类元数据，用于缓存，避免多次解析 */
    protected ClassMeta clazz;

    /**
     * 解析类符号引用
     */
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
