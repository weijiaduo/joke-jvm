package com.wjd.rtda.heap;

import com.wjd.rtda.Slot;

/**
 * 对象基类
 * @since 2022/1/30
 */
public class Object {

    private Class clazz;
    private Slot[] fields;

    public static Object newObject(Class clazz) {
        Object obj = new Object();
        obj.clazz = clazz;
        obj.fields = new Slot[clazz.getInstanceSlotCount()];
        for (int i = 0; i < obj.fields.length; i++) {
            obj.fields[i] = new Slot();
        }
        return obj;
    }

    public Class getClazz() {
        return clazz;
    }

    public Slot[] getFields() {
        return fields;
    }

    public boolean isInstanceOf(Class cls) {
        return cls.isAssignableFrom(clazz);
    }
}
