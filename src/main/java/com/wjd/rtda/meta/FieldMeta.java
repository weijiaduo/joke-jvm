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

    /** 字段在类中的id，有这个id就能在类那边拿到这个字段 */
    private int slotId;
    /** 字段的类型 */
    private ClassMeta type;
    /** 指向ClassFile常量池的索引 */
    private Uint16 constValueIndex;
    /** 是否是long或者double类型 */
    private boolean isLongOrDouble;

    public static FieldMeta[] newFields(ClassMeta classMeta, FieldInfo[] fieldInfos) {
        FieldMeta[] fields = new FieldMeta[fieldInfos.length];
        for (int i = 0; i < fields.length; i++) {
            fields[i] = newField(classMeta, fieldInfos[i]);
        }
        return fields;
    }

    public static FieldMeta newField(ClassMeta classMeta, FieldInfo fieldInfo) {
        FieldMeta field = new FieldMeta();
        field.classMeta = classMeta;
        field.copyMemberInfo(fieldInfo);
        field.copyFieldAttribute(fieldInfo);
        field.isLongOrDouble = "J".equals(field.descriptor) || "D".equals(field.descriptor);
        return field;
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
        return classMeta.getLoader().loadClass(typeClassName);
    }

    public void putStaticValue(Slot slot) {
        classMeta.getStaticVars()[slotId].setSlot(slot);
    }

    public Slot getStaticValue() {
        return classMeta.getStaticVars()[slotId];
    }

    @Override
    public String toString() {
        return "FieldMeta{" +
                "slotId=" + slotId +
                ", name='" + name + '\'' +
                ", descriptor='" + descriptor + '\'' +
                ", signature='" + signature + '\'' +
                ", classMeta=" + classMeta +
                '}';
    }
}
