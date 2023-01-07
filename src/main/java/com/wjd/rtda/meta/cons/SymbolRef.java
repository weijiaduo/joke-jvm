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
    protected ClassMeta classMeta;

    /**
     * 解析类符号引用
     */
    public ClassMeta resolvedClass() {
        if (classMeta == null) {
            resolveClassRef();
        }
        return classMeta;
    }

    /**
     * 解析类符号引用
     */
    private void resolveClassRef() {
        ClassMeta currentClassMeta = constantPool.getClassMeta();
        ClassMeta refClassMeta = currentClassMeta.getLoader().loadClass(className);
        if (!refClassMeta.isAccessibleTo(currentClassMeta)) {
            throw new IllegalAccessError("Class " + currentClassMeta.getName()
                    + " can not access Class " + refClassMeta.getName());
        }
        classMeta = refClassMeta;
    }

}
