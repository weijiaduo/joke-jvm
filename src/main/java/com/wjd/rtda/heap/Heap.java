package com.wjd.rtda.heap;

import com.wjd.rtda.meta.ClassMeta;

/**
 * @since 2022/2/19
 */
public final class Heap {

    public static HeapObject newObject(ClassMeta clazz) {
        HeapObject obj = new HeapObject(clazz);
        return obj;
    }

    public static HeapObject newObject(ClassMeta clazz, Object data) {
        HeapObject obj = new HeapObject(clazz, data);
        return obj;
    }

}
