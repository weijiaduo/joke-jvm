package com.wjd.rtda.meta;

import com.wjd.classfile.member.FieldInfo;
import com.wjd.classfile.type.Uint16;

/**
 * 字段成员
 * @since 2022/1/30
 */
public class FieldMeta extends MemberMeta {

    private int slotId;
    private boolean isLongOrDouble;
    private Uint16 constValueIndex;

    public static FieldMeta[] newFields(ClassMeta clazz, FieldInfo[] fieldInfos) {
        FieldMeta[] fieldMetas = new FieldMeta[fieldInfos.length];
        for (int i = 0; i < fieldMetas.length; i++) {
            fieldMetas[i] = new FieldMeta();
            fieldMetas[i].clazz = clazz;
            fieldMetas[i].copyMemberInfo(fieldInfos[i]);
            fieldMetas[i].copyFieldAttribute(fieldInfos[i]);
            fieldMetas[i].isLongOrDouble = "J".equals(fieldMetas[i].descriptor) || "D".equals(fieldMetas[i].descriptor);
        }
        return fieldMetas;
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
