package com.wjd.util;

import com.wjd.rtda.Slot;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.MethodMeta;

/**
 * @since 2022/2/17
 */
public final class MethodHelper {

    /**
     * 获取构造方法元数据
     * @param constructorObj 构造方法对象
     */
    public static MethodMeta getObjConstructor(HeapObject constructorObj) {
        return getObjMethod(constructorObj, true);
    }

    /**
     * 获取方法实例对应的方法元数据
     * @param methodObj 方法实例
     * @param isConstructor 是否是构造函数
     */
    public static MethodMeta getObjMethod(HeapObject methodObj, boolean isConstructor) {
        Object extra = methodObj.getExtra();
        if (extra != null) {
            return (MethodMeta) extra;
        }
        HeapObject root;
        if (isConstructor) {
            root = methodObj.getRefVar("root", "Ljava/lang/reflect/Constructor;");
        } else {
            root = methodObj.getRefVar("root", "Ljava/lang/reflect/Method;");
        }
        return (MethodMeta) root.getExtra();
    }

    /**
     * 把参数对象转换为Slot[]数组
     * @param that 当前上下文对象this
     * @param argsArr 参数数组对象
     * @param method 方调用法元数据
     */
    public static Slot[] convertArgs(HeapObject that, HeapObject argsArr, MethodMeta method) {
        if (method.getArgSlotCount() == 0) {
            return null;
        }

        Slot[] args = new Slot[method.getArgSlotCount()];
        int index = 0;

        // 实例方法，第一个参数是this
        if (!method.isStatic()) {
            Slot thisSlot = new Slot();
            thisSlot.setRef(that);
            args[index++] = thisSlot;
            if (args.length == 1) {
                return args;
            }
        }

        HeapObject[] argsObjs = argsArr.getRefs();
        ClassMeta[] argsTypes = method.getParameterTypes();
        for (int i = 0; i < argsTypes.length; i++) {
            HeapObject argsObj = argsObjs[i];
            ClassMeta argsType = argsTypes[i];
            String typeName = argsType.getName();
            if (typeName.length() == 1) {
                // 基本类型，自动拆箱
                switch (typeName) {
                    case "Z":
                    case "B":
                    case "C":
                    case "S":
                    case "I":
                    case "F":
                    {
                        int val = argsObj.getIntVar("value", typeName);
                        Slot slot = new Slot(val);
                        args[index++] = slot;
                        break;
                    }
                    case "J":
                    case "D":
                    {
                        long val = argsObj.getLongVar("value", typeName);
                        Slot highSlot = new Slot();
                        Slot lowSlot = new Slot();
                        Slot.setLong(highSlot, lowSlot, val);
                        args[index++] = highSlot;
                        args[index++] = lowSlot;
                    }
                    default:
                        System.out.println("Unknown box type: " + typeName);
                }
            } else {
                // 引用类型
                Slot slot = new Slot();
                slot.setRef(argsObj);
                args[index++] = slot;
            }
        }

        return args;
    }

}
