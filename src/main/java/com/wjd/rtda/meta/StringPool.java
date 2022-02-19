package com.wjd.rtda.meta;

import com.wjd.rtda.heap.Heap;
import com.wjd.rtda.heap.HeapObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 字符串常量池
 * @since 2022/2/11
 */
public class StringPool {

    /** 字符串常量 */
    private static Map<String, HeapObject> internedStrings = new HashMap<>();

    /**
     * 获取String对象
     */
    public static HeapObject getStringObj(ClassMetaLoader loader, String string) {
        if (internedStrings.containsKey(string)) {
            return internedStrings.get(string);
        }

        char[] chars = string.toCharArray();
        HeapObject charObj = Heap.newObject(loader.loadClass("[C"), chars);
        HeapObject stringObj = loader.loadClass("java/lang/String").newObject();
        stringObj.setRefVar("value", "[C", charObj);
        internedStrings.put(string, stringObj);
        return stringObj;
    }

    /**
     * 获取String对象对应的字符串
     */
    public static String getRawString(HeapObject stringObj) {
        if (stringObj == null) {
            return null;
        }
        HeapObject value = stringObj.getRefVar("value", "[C");
        char[] chars = value.getChars();
        return new String(chars);
    }

    /**
     * 把String对象放入字符串常量池
     */
    public static HeapObject internString(HeapObject stringObj) {
        String str = getRawString(stringObj);
        if (internedStrings.containsKey(str)) {
            return internedStrings.get(str);
        }
        internedStrings.put(str, stringObj);
        return stringObj;
    }

    /**
     * 创建String数组对象
     */
    public static HeapObject createStringArray(ClassMetaLoader loader, String[] args) {
        ClassMeta stringClassMeta = loader.loadClass("java/lang/String");
        HeapObject argsArray = stringClassMeta.getArrayClass().newArray(args.length);
        HeapObject[] refs = argsArray.getRefs();
        for (int i = 0; i < args.length; i++) {
            refs[i] = StringPool.getStringObj(loader, args[i]);
        }
        return argsArray;
    }
}
