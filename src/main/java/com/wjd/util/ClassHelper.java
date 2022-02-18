package com.wjd.util;

import com.wjd.rtda.meta.PrimitiveMeta;

/**
 * @since 2022/2/17
 */
public final class ClassHelper {

    /**
     * 从描述符中解析出类名
     * @param descriptor 描述符
     * @return 类名
     */
    public static String getClassName(String descriptor) {
        // 数组类型
        if (descriptor.charAt(0) == '[') {
            return descriptor;
        }
        // 引用类型
        if (descriptor.charAt(0) == 'L') {
            return descriptor.substring(1, descriptor.length() - 1);
        }
        // 基本类型
        for (String className : PrimitiveMeta.primitiveTypes.keySet()) {
            if (descriptor.equals(PrimitiveMeta.primitiveTypes.get(className))) {
                return className;
            }
        }
        throw new IllegalArgumentException("Invalid descriptor: " + descriptor);
    }

}
