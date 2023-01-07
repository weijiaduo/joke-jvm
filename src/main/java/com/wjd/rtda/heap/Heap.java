package com.wjd.rtda.heap;

import com.wjd.rtda.meta.ClassMeta;
import com.wjd.util.ArrayHelper;

/**
 * @since 2022/2/19
 */
public final class Heap {

    public static HeapObject newObject(ClassMeta classMeta) {
        return new HeapObject(classMeta);
    }

    public static HeapObject newObject(ClassMeta classMeta, Object data) {
        return new HeapObject(classMeta, data);
    }

    public static HeapObject newArray(ClassMeta classMeta, int count) {
        if (!classMeta.isArray()) {
            throw new IllegalStateException("Not array class: " + classMeta.getName());
        }
        return newObject(classMeta, ArrayHelper.makeArray(classMeta.getName(), count));
    }

}
