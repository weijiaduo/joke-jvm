package com.wjd.classfile.member;

import com.wjd.classfile.attr.AttributeInfo;
import com.wjd.classfile.attr.CodeAttributeInfo;
import com.wjd.classfile.attr.ExceptionsAttributeInfo;

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

    /**
     * 获取异常信息属性
     */
    public ExceptionsAttributeInfo getExceptionsAttributeInfo() {
        for (AttributeInfo attr : attributeTable.getAttributes()) {
            if (attr instanceof ExceptionsAttributeInfo) {
                return (ExceptionsAttributeInfo) attr;
            }
        }
        return null;
    }

}
