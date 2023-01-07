package com.wjd.util;

import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.FieldMeta;

/**
 * @since 2022/2/20
 */
public final class FieldHelper {

    /**
     * 查找指定的字段元数据
     * @param classMeta 类元数据
     * @param name 字段名称
     * @param descriptor 字段描述符
     * @return 字段元数据
     */
    public static FieldMeta lookupField(ClassMeta classMeta, String name, String descriptor) {
        // 当前类声明的字段
        for (FieldMeta field : classMeta.getFields()) {
            if (field.getName().equals(name) && field.getDescriptor().equals(descriptor)) {
                return field;
            }
        }
        // 接口声明的字段
        ClassMeta[] interfaces = classMeta.getInterfaces();
        for (ClassMeta c : interfaces) {
            FieldMeta f = lookupField(c, name, descriptor);
            if (f != null) {
                return f;
            }
        }
        // 父类声明的字段
        if (classMeta.getSuperClass() != null) {
            return lookupField(classMeta.getSuperClass(), name, descriptor);
        }
        return null;
    }

}
