package com.wjd.naive;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @since 2022/2/12
 */
public class NativeRegistry {

    private static Set<String> loadedClass = new HashSet<>();
    private static Map<String, NativeMethod> registry = new HashMap<>();
    private static NativeMethod emptyMethod = new EmptyNativeMethod();

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
        loadNativeClass(className);
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
     * 加载本地类
     * @param className 类名称
     */
    private static void loadNativeClass(String className) {
        if (loadedClass.contains(className)) {
            return;
        }
        try {
            // 先添加到集合，避免异常
            loadedClass.add(className);
            className = className.replaceAll("/", ".");
            String basePackage = NativeRegistry.class.getPackage().getName();

            // 使用Class.forName()可以让static执行注册
            Class.forName(basePackage + "." + className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
