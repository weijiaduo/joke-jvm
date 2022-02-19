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

    private FieldMeta field;

    public static FieldRef newFieldRef(ConstantPool constantPool, FieldRefConstantInfo constantInfo) {
        FieldRef ref = new FieldRef();
        ref.constantPool = constantPool;
        ref.copyMemberRefInfo(constantInfo);
        return ref;
    }

    /**
     * 解析字段符号引用，返回字段元数据
     */
    public FieldMeta resolvedField() {
        if (field == null) {
            resolveFieldRef();
        }
        return field;
    }

    /**
     * 解析字段符号引用
     */
    private void resolveFieldRef() {
        ClassMeta currentClazz = constantPool.getClazz();
        ClassMeta refClazz = resolvedClass();
        FieldMeta refField = lookupField(refClazz, name, descriptor);
        if (refField == null) {
            throw new NoSuchFieldError("No such field: " + name + ", " + descriptor);
        }
        if (!refField.isAccessibleTo(currentClazz)) {
            throw new IllegalAccessError(("Class " + currentClazz.getName() + " can not assess Field " + name));
        }
        field = refField;
    }

    /**
     * 查找指定的字段元数据
     * @param clazz 类元数据
     * @param name 字段名称
     * @param descriptor 字段描述符
     * @return 字段元数据
     */
    private static FieldMeta lookupField(ClassMeta clazz, String name, String descriptor) {
        // 当前类声明的字段
        for (FieldMeta field : clazz.getFields()) {
            if (field.getName().equals(name) && field.getDescriptor().equals(descriptor)) {
                return field;
            }
        }
        // 接口声明的字段
        ClassMeta[] interfaces = clazz.getInterfaces();
        for (ClassMeta c : interfaces) {
            FieldMeta f = lookupField(c, name, descriptor);
            if (f != null) {
                return f;
            }
        }
        // 父类声明的字段
        if (clazz.getSuperClass() != null) {
            return lookupField(clazz.getSuperClass(), name, descriptor);
        }
        return null;
    }


}
