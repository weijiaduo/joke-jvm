package com.wjd.rtda.meta.util;

import com.wjd.rtda.meta.MethodDescriptor;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 2022/2/7
 */
public class MethodDescriptorParser {

    private StringBuffer str;

    public MethodDescriptorParser(String string) {
        str = new StringBuffer(string);
    }

    public static MethodDescriptor parseFrom(String descriptor) {
        return new MethodDescriptorParser(descriptor).parse();
    }

    public MethodDescriptor parse() {
        String[] paramTypes = parseParamTypes();
        String returnType = parseReturnType();
        if (paramTypes == null || returnType == null) {
            return null;
        }

        MethodDescriptor methodDescriptor = new MethodDescriptor();
        methodDescriptor.setParameterTypes(paramTypes);
        methodDescriptor.setReturnType(returnType);
        return methodDescriptor;
    }

    private String[] parseParamTypes() {
        if (str == null || str.length() == 0 || str.charAt(0) != '(') {
            return null;
        }

        // 删除左括号
        str.deleteCharAt(0);

        // 解析字段类型
        List<String> types = new ArrayList<>();
        while (str.length() > 0) {
            String type = parseFieldType();
            if (type == null) {
                break;
            }
            types.add(type);
        }

        if (str.length() == 0 || str.charAt(0) != ')') {
            return null;
        }

        // 删除右括号
        str.deleteCharAt(0);

        return types.toArray(new String[0]);
    }

    private String parseReturnType() {
        String type = parseFieldType();
        if (type != null) {
            if (str.length() != 0) {
                return null;
            }
            return type;
        }
        return "V";
    }

    private String parseFieldType() {
        if (str.length() <= 0) {
            return null;
        }
        switch (str.charAt(0)) {
            case 'B':
            case 'C':
            case 'D':
            case 'F':
            case 'I':
            case 'J':
            case 'S':
            case 'Z':
                String type = str.substring(0, 1);
                str.deleteCharAt(0);
                return type;
            case 'L':
                return parseObjectType();
            case '[':
                return parseArrayType();
        }
        return null;
    }

    private String parseObjectType() {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ';') {
                String type = str.substring(0, i + 1);
                str.delete(0, i + 1);
                return type;
            }
        }
        return null;
    }

    private String parseArrayType() {
        str.deleteCharAt(0);
        String type = parseFieldType();
        if (type == null) {
            return null;
        }
        return "[" + type;
    }

}
