package com.wjd.classfile.member;

import com.wjd.classfile.attr.AttributeInfo;
import com.wjd.classfile.attr.ConstantValueAttributeInfo;
import com.wjd.classfile.type.Uint16;

public class FieldInfo extends MemberInfo {

    /**
     * 常量值索引
     */
    public Uint16 getConstValueIndex() {
        for (AttributeInfo attr : getAttributes()) {
            if (attr instanceof ConstantValueAttributeInfo) {
                return ((ConstantValueAttributeInfo) attr).getConstantIndex();
            }
        }
        return null;
    }

}
