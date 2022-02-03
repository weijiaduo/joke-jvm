package com.wjd.rtda.heap;

import com.wjd.classfile.ClassFile;
import com.wjd.classfile.ClassReader;
import com.wjd.classfile.type.Uint16;
import com.wjd.cp.Classpath;
import com.wjd.rtda.Slot;
import com.wjd.rtda.heap.cons.*;
import com.wjd.rtda.heap.member.Field;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @since 2022/1/30
 */
public class ClassLoader {

    private Classpath classpath;
    private Map<String, Class> classMap;

    public static ClassLoader newClassLoader(Classpath classpath) {
        ClassLoader classLoader = new ClassLoader();
        classLoader.classpath = classpath;
        classLoader.classMap = new HashMap<>();
        return classLoader;
    }

    /**
     * 加载指定类
     * @param name 类名称
     */
    public Class loadClass(String name) {
        if (classMap.containsKey(name)) {
            return classMap.get(name);
        }
        try {
            return loadNonArrayClass(name);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Load class error: " + name);
        }
    }

    /**
     * 加载非数组的基础类型
     * @param name 类名称
     */
    protected Class loadNonArrayClass(String name) throws IOException {
        byte[] classBytes = readClass(name);
        Class clazz = defineClass(classBytes);
        link(clazz);
        System.out.println("Loaded Class: " + name);
        return clazz;
    }

    /**
     * 查找class对应的字节码
     * @param name 类名称
     */
    protected byte[] readClass(String name) throws IOException {
        return classpath.readClass(name);
    }

    /**
     * 定义class类
     * @param classBytes class字节码
     * @return Class类
     */
    protected Class defineClass(byte[] classBytes) {
        Class clazz = parseClass(classBytes);
        clazz.setLoader(this);
        resolveSuperClass(clazz);
        resolveInterfaces(clazz);
        classMap.put(clazz.getName(), clazz);
        return clazz;
    }

    /**
     * 解析class字节码
     * @param classBytes class字节码
     * @return Class类
     */
    protected Class parseClass(byte[] classBytes) {
        ClassReader reader = new ClassReader(classBytes);
        ClassFile classFile = ClassFile.parse(reader);
        return Class.newClass(classFile);
    }

    /**
     * 加载父类
     */
    protected void resolveSuperClass(Class clazz) {
        if ("java/lang/Object".equals(clazz.getName())) {
            return;
        }
        clazz.setSuperClass(clazz.getLoader().loadClass(clazz.getSuperClassName()));
    }

    /**
     * 加载接口
     */
    protected void resolveInterfaces(Class clazz) {
        String[] interfaceNames = clazz.getInterfaceNames();
        if (interfaceNames == null) {
            return;
        }
        Class[] interfaces = new Class[interfaceNames.length];
        for (int i = 0; i < interfaces.length; i++) {
            interfaces[i] = clazz.getLoader().loadClass(interfaceNames[i]);
        }
        clazz.setInterfaces(interfaces);
    }

    /**
     * 链接阶段
     */
    protected void link(Class clazz) {
        verify(clazz);
        prepare(clazz);
    }

    /**
     * 验证阶段
     */
    protected void verify(Class clazz) {
        // do nothing
    }

    /**
     * 准备阶段
     */
    protected void prepare(Class clazz) {
        calcInstanceFieldSlotIds(clazz);
        calcStaticFieldSlotIds(clazz);
        allocAndInitStaticVars(clazz);
    }

    /**
     * 计算实例字段数量
     */
    private void calcInstanceFieldSlotIds(Class clazz) {
        int slotId = 0;
        // 父类的字段数量
        if (clazz.getSuperClass() != null) {
            slotId = clazz.getSuperClass().getInstanceSlotCount();
        }
        // 类实例字段数量
        for (Field field : clazz.getFields()) {
            if (!field.isStatic()) {
                field.setSlotId(slotId);
                slotId++;
                if (field.isLongOrDouble()) {
                    slotId++;
                }
            }
        }
        clazz.setInstanceSlotCount(slotId);
    }

    /**
     * 计算静态字段
     */
    private void calcStaticFieldSlotIds(Class clazz) {
        int slotId = 0;
        for (Field field : clazz.getFields()) {
            if (field.isStatic()) {
                field.setSlotId(slotId);
                slotId++;
                if (field.isLongOrDouble()) {
                    slotId++;
                }
            }
        }
        clazz.setStaticSlotCount(slotId);
    }

    /**
     * 分配静态变量空间以及初始化静态常量值
     */
    private void allocAndInitStaticVars(Class clazz) {
        Slot[] slots = new Slot[clazz.getStaticSlotCount()];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = new Slot();
        }
        clazz.setStaticVars(slots);
        for (Field field : clazz.getFields()) {
            if (field.isStatic() && field.isFinal()) {
                initStaticFinalVars(clazz, field);
            }
        }
    }

    /**
     * 初始化静态常量值
     */
    private void initStaticFinalVars(Class clazz, Field field) {
        Slot[] vars = clazz.getStaticVars();
        ConstantPool cp = clazz.getConstantPool();
        Uint16 constValueIndex = field.getConstValueIndex();
        if (constValueIndex == null) {
            return;
        }
        int slotId = field.getSlotId();
        Constant constant = cp.getConstant(constValueIndex.value());
        switch (field.getDescriptor()) {
            case "Z":
            case "B":
            case "C":
            case "S":
            case "I":
            {
                int val = ((IntegerConstant) constant).value();
                Slot.setInt(vars[slotId], val);
                break;
            }
            case "J":
            {
                long val = ((LongConstant) constant).value();
                Slot.setLong(vars[slotId], vars[slotId + 1], val);
                break;
            }
            case "F":
            {
                float val = ((FloatConstant) constant).value();
                Slot.setFloat(vars[slotId], val);
                break;
            }
            case "D":
            {
                double val = ((DoubleConstant) constant).value();
                Slot.setDouble(vars[slotId], vars[slotId + 1], val);
                break;
            }
            case "Ljava/lang/String;":
            {
                System.out.println("Unsupported String constant now~");
                break;
            }
            default:
                System.out.println("Unknown field constant: " + field.getDescriptor());
        }
    }

}
