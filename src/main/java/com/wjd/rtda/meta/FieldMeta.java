package com.wjd.rtda.meta;

import com.wjd.classfile.FieldInfo;
import com.wjd.classfile.type.Uint16;
import com.wjd.rtda.Slot;
import com.wjd.util.ClassHelper;

/**
 * 字段成员
 * @since 2022/1/30
 */
public class FieldMeta extends MemberMeta {

    private int slotId;
    private boolean isLongOrDouble;
    private Uint16 constValueIndex;

    private ClassMeta type;

    public static FieldMeta[] newFields(ClassMeta clazz, FieldInfo[] fieldInfos) {
        FieldMeta[] fields = new FieldMeta[fieldInfos.length];
        for (int i = 0; i < fields.length; i++) {
            fields[i] = new FieldMeta();
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

    public ClassMeta getType() {
        if (type == null) {
            type = resolvedType();
        }
        return type;
    }

    private ClassMeta resolvedType() {
        String typeClassName = ClassHelper.getClassName(descriptor);
        ClassMeta type = clazz.getLoader().loadClass(typeClassName);
        return type;
    }

    public void putStaticValue(Slot slot) {
        clazz.getStaticVars()[slotId].setSlot(slot);
    }

    public Slot getStaticValue() {
        return clazz.getStaticVars()[slotId];
    }
}
