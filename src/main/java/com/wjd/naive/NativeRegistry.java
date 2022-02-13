package com.wjd.naive;

import java.util.HashMap;
import java.util.Map;

/**
 * @since 2022/2/12
 */
public class NativeRegistry {

    private static Map<String, NativeMethod> registry = new HashMap<>();
    private static NativeMethod emptyMethod = new EmptyNativeMethod();

    private static boolean initRegistry;
    static {
        initRegistry = initRegistryNativeClass();
    }

    /**
     * 注册本地方法
     */
    public static void registry(String className, String methodName, String methodDescriptor, NativeMethod method) {
        String key = getKey(className, methodName, methodDescriptor);
        registry.put(key, method);
    }

    /**
     * 查找指定的本地方法实现
     */
    public static NativeMethod findNativeMethod(String className, String methodName, String methodDescriptor) {
        String key = getKey(className, methodName, methodDescriptor);
        NativeMethod method = registry.get(key);
        if (method != null) {
            return method;
        }
        // 暂时返回一个空的本地方法实现
        if ("()V".equals(methodDescriptor) && "registerNatives".equals(methodName)) {
            return emptyMethod;
        }
        return null;
    }

    private static String getKey(String className, String methodName, String methodDescriptor) {
        return className + "~" + methodName + "~" + methodDescriptor;
    }

    /**
     * FIXME: 临时方法
     * 注册本地方法类
     */
    private static boolean initRegistryNativeClass() {
        try {
            String basePackageName = "com.wjd.naive.";
            Class.forName(basePackageName + "java.lang.Object");
            Class.forName(basePackageName + "java.lang.Class");
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

}
