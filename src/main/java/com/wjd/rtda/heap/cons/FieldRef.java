package com.wjd.rtda.heap.cons;

import com.wjd.classfile.cons.FieldRefConstantInfo;
import com.wjd.rtda.heap.Class;
import com.wjd.rtda.heap.ConstantPool;
import com.wjd.rtda.heap.member.Field;

/**
 * 字段引用
 * @since 2022/1/30
 */
public class FieldRef extends MemberRef {

    private Field field;

    public static FieldRef newFieldRef(ConstantPool constantPool, FieldRefConstantInfo constantInfo) {
        FieldRef ref = new FieldRef();
        ref.constantPool = constantPool;
        ref.copyMemberRefInfo(constantInfo);
        return ref;
    }

    public Field resolvedField() {
        if (field == null) {
            resolveFieldRef();
        }
        return field;
    }

    private void resolveFieldRef() {
        Class currentClass = constantPool.getClazz();
        Class refClass = resolvedClass();
        Field refField = lookupField(refClass, name, descriptor);
        if (refField == null) {
            throw new NoSuchFieldError("No such field: " + name + ", " + descriptor);
        }
        if (!refField.isAccessibleTo(currentClass)) {
            throw new IllegalAccessError(("Class " + currentClass.getName() + " can not assess Field " + name));
        }
        field = refField;
    }

    private static Field lookupField(Class clazz, String name, String descriptor) {
        for (Field field : clazz.getFields()) {
            if (field.getName().equals(name) && field.getDescriptor().equals(descriptor)) {
                return field;
            }
        }
        Class[] interfaces = clazz.getInterfaces();
        for (Class c : interfaces) {
            Field f = lookupField(c, name, descriptor);
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
