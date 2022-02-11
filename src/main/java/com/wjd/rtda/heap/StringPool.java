package com.wjd.rtda.heap;

import java.util.HashMap;
import java.util.Map;

/**
 * 字符串常量池
 * @since 2022/2/11
 */
public class StringPool {

    /** 字符串常量 */
    private static Map<String, HeapObject> internedStrings = new HashMap<>();

    public static HeapObject getJString(ClassLoader loader, String string) {
        if (internedStrings.containsKey(string)) {
            return internedStrings.get(string);
        }
        char[] chars = string.toCharArray();
        HeapObject charObj = HeapObject.newArray(loader.loadClass("[C"), chars);
        HeapObject stringObj = loader.loadClass("java/lang/String").newObject();
        stringObj.setRefVar("value", "[C", charObj);
        internedStrings.put(string, stringObj);
        return stringObj;
    }

    public static String getString(HeapObject stringObj) {
        HeapObject value = stringObj.getRefVar("value", "[C");
        char[] chars = value.getChars();
        return new String(chars);
    }
}
