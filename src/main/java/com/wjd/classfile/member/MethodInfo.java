package com.wjd.classfile.member;

import com.wjd.classfile.attr.AttributeInfo;
import com.wjd.classfile.attr.CodeAttributeInfo;

public class MethodInfo extends MemberInfo {

    /**
     * 获取代码属性
     * @return 代码属性
     */
    public CodeAttributeInfo getCodeAttributeInfo() {
        for (AttributeInfo attr : attributeTable.getAttributes()) {
            if (attr instanceof CodeAttributeInfo) {
                return (CodeAttributeInfo) attr;
            }
        }
        return null;
    }

}
