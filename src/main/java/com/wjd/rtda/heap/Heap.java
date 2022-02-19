package com.wjd.rtda.heap;

import com.wjd.rtda.meta.ClassMeta;
import com.wjd.util.ArrayHelper;

/**
 * @since 2022/2/19
 */
public final class Heap {

    public static HeapObject newObject(ClassMeta clazz) {
        return new HeapObject(clazz);
    }

    public static HeapObject newObject(ClassMeta clazz, Object data) {
        return new HeapObject(clazz, data);
    }

    public static HeapObject newArray(ClassMeta clazz, int count) {
        if (!clazz.isArray()) {
            throw new IllegalStateException("Not array class: " + clazz.getName());
        }
        return newObject(clazz, ArrayHelper.makeArray(clazz.getName(), count));
    }

}
