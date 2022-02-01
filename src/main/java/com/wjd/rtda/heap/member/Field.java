package com.wjd.rtda.heap.member;

import com.wjd.classfile.member.FieldInfo;
import com.wjd.classfile.type.Uint16;
import com.wjd.rtda.heap.Class;

/**
 * 字段成员
 * @since 2022/1/30
 */
public class Field extends ClassMember {

    private int slotId;
    private boolean isLongOrDouble;
    private Uint16 constValueIndex;

    public static Field[] newFields(Class clazz, FieldInfo[] fieldInfos) {
        Field[] fields = new Field[fieldInfos.length];
        for (int i = 0; i < fields.length; i++) {
            fields[i] = new Field();
            fields[i].clazz = clazz;
            fields[i].copyMemberInfo(fieldInfos[i]);
            fields[i].copyFieldAttribute(fieldInfos[i]);
            fields[i].isLongOrDouble = "J".equals(fields[i].descriptor) || "D".equals(fields[i].descriptor);
        }
        return fields;
    }

    private void copyFieldAttribute(FieldInfo fieldInfo) {
        constValueIndex = fieldInfo.getConstValueIndex();
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public boolean isLongOrDouble() {
        return isLongOrDouble;
    }

    public Uint16 getConstValueIndex() {
        return constValueIndex;
    }
}
