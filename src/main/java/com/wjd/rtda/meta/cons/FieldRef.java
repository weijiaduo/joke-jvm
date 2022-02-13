package com.wjd.rtda.meta.cons;

import com.wjd.classfile.cons.FieldRefConstantInfo;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.ConstantPool;
import com.wjd.rtda.meta.FieldMeta;

/**
 * 字段引用
 * @since 2022/1/30
 */
public class FieldRef extends MemberRef {

    private FieldMeta fieldMeta;

    public static FieldRef newFieldRef(ConstantPool constantPool, FieldRefConstantInfo constantInfo) {
        FieldRef ref = new FieldRef();
        ref.constantPool = constantPool;
        ref.copyMemberRefInfo(constantInfo);
        return ref;
    }

    public FieldMeta resolvedField() {
        if (fieldMeta == null) {
            resolveFieldRef();
        }
        return fieldMeta;
    }

    private void resolveFieldRef() {
        ClassMeta currentClassMeta = constantPool.getClazz();
        ClassMeta refClassMeta = resolvedClass();
        FieldMeta refFieldMeta = lookupField(refClassMeta, name, descriptor);
        if (refFieldMeta == null) {
            throw new NoSuchFieldError("No such field: " + name + ", " + descriptor);
        }
        if (!refFieldMeta.isAccessibleTo(currentClassMeta)) {
            throw new IllegalAccessError(("Class " + currentClassMeta.getName() + " can not assess Field " + name));
        }
        fieldMeta = refFieldMeta;
    }

    private static FieldMeta lookupField(ClassMeta clazz, String name, String descriptor) {
        for (FieldMeta fieldMeta : clazz.getFields()) {
            if (fieldMeta.getName().equals(name) && fieldMeta.getDescriptor().equals(descriptor)) {
                return fieldMeta;
            }
        }
        ClassMeta[] interfaces = clazz.getInterfaces();
        for (ClassMeta c : interfaces) {
            FieldMeta f = lookupField(c, name, descriptor);
            if (f != null) {
                return f;
            }
        }
        if (clazz.getSuperClass() != null) {
            return lookupField(clazz.getSuperClass(), name, descriptor);
        }
        return null;
    }


}
