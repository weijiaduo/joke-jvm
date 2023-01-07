package com.wjd.rtda.meta.cons;

import com.wjd.classfile.cons.FieldRefConstantInfo;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.ConstantPool;
import com.wjd.rtda.meta.FieldMeta;
import com.wjd.util.FieldHelper;

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
        ClassMeta currentClassMeta = constantPool.getClassMeta();
        ClassMeta refClassMeta = resolvedClass();
        FieldMeta refField = FieldHelper.lookupField(refClassMeta, name, descriptor);
        if (refField == null) {
            throw new NoSuchFieldError("No such field: " + name + ", " + descriptor);
        }
        if (!refField.isAccessibleTo(currentClassMeta)) {
            throw new IllegalAccessError(("Class " + currentClassMeta.getName() + " can not assess Field " + name));
        }
        field = refField;
    }

}
