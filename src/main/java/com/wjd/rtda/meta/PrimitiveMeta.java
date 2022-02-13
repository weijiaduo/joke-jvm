package com.wjd.rtda.meta;

import java.util.HashMap;
import java.util.Map;

/**
 * @since 2022/2/13
 */
public class PrimitiveMeta {

    public static Map<String, String> primitiveTypes;
    static {
        primitiveTypes = new HashMap<>();
        primitiveTypes.put("void", "V");
        primitiveTypes.put("boolean", "Z");
        primitiveTypes.put("byte", "B");
        primitiveTypes.put("char", "C");
        primitiveTypes.put("short", "S");
        primitiveTypes.put("int", "I");
        primitiveTypes.put("long", "J");
        primitiveTypes.put("float", "F");
        primitiveTypes.put("double", "D");
    }

    public static Map<String, String> primitiveJTypes;
    static {
        primitiveJTypes = new HashMap<>();
        primitiveJTypes.put("void", "java/lang/Void");
        primitiveJTypes.put("boolean", "java/lang/Boolean");
        primitiveJTypes.put("byte", "java/lang/Byte");
        primitiveJTypes.put("char", "java/lang/Character");
        primitiveJTypes.put("short", "java/lang/Short");
        primitiveJTypes.put("int", "java/lang/Integer");
        primitiveJTypes.put("long", "java/lang/Long");
        primitiveJTypes.put("float", "java/lang/Float");
        primitiveJTypes.put("double", "java/lang/Double");
    }

}
