package com.wjd.rtda.meta;

import com.wjd.classfile.ClassFile;
import com.wjd.classfile.type.Uint16;
import com.wjd.classpath.Classpath;
import com.wjd.rtda.AccessFlags;
import com.wjd.rtda.Slot;
import com.wjd.rtda.heap.Heap;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.cons.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 类加载器
 * @since 2022/1/30
 */
public class ClassMetaLoader {

    /** 类加载路径 */
    private Classpath classpath;
    /** 是否打印类加载信息 */
    private boolean verboseLoadClass = false;
    /** 已加载的类 */
    private Map<String, ClassMeta> classMap;

    public static ClassMetaLoader newClassLoader(Classpath classpath, boolean verboseFlag) {
        ClassMetaLoader classMetaLoader = new ClassMetaLoader();
        classMetaLoader.classpath = classpath;
        classMetaLoader.classMap = new HashMap<>();
        classMetaLoader.verboseLoadClass = verboseFlag;
        // 加载初始化基类对象
        classMetaLoader.loadBasicClasses();
        // 加载基本类型
        classMetaLoader.loadPrimitiveClasses();
        return classMetaLoader;
    }

    /**
     * 加载类元数据对应的java/lang/Class类
     * @param clazz 类元数据
     */
    private void loadJClass(ClassMeta clazz) {
        if (clazz.getjClass() != null) {
            return;
        }
        ClassMeta jlClassClass = classMap.get("java/lang/Class");
        if (jlClassClass != null) {
            // 为每个类型都生成一个java.lang.Class对象
            clazz.setjClass(Heap.newObject(jlClassClass));
            clazz.getjClass().setExtra(clazz);
        }
    }

    /**
     * 加载基础类对象
     */
    private void loadBasicClasses() {
        for (String className : classMap.keySet()) {
            ClassMeta clazz = classMap.get(className);
            loadJClass(clazz);
        }
    }

    /**
     * 加载基本类型
     */
    private void loadPrimitiveClasses() {
        for (String className : PrimitiveMeta.primitiveTypes.keySet()) {
            loadPrimitiveClass(className);
        }
    }

    /**
     * 加载基本类型
     * 基本类型的类名就是boolean、int、long等
     */
    private void loadPrimitiveClass(String className) {
        ClassMeta clazz = new ClassMeta();
        clazz.setAccessFlags(new Uint16(AccessFlags.ACCPUBLIC));
        clazz.setName(className);
        clazz.setLoader(this);
        clazz.startInit();
        loadJClass(clazz);
        classMap.put(className, clazz);
    }

    /**
     * 加载指定类
     * @param name 类名称
     */
    public ClassMeta loadClass(String name) {
        try {
            // 类已加载过
            ClassMeta clazz = classMap.get(name);
            if (clazz != null) {
                return clazz;
            }

            if (name.charAt(0) == '[') {
                // 数组类型
                clazz = loadArrayClass(name);
            } else {
                // 非数组类型
                clazz = loadNonArrayClass(name);
            }

            // 为每个类型都生成一个java.lang.Class对象
            loadJClass(clazz);
            return clazz;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Load class error: " + name);
        }
    }

    /**
     * 加载数组类型
     */
    protected ClassMeta loadArrayClass(String name) {
        ClassMeta arrayClazz = new ClassMeta();
        arrayClazz.setLoader(this);
        arrayClazz.setAccessFlags(new Uint16(AccessFlags.ACCPUBLIC));
        arrayClazz.setName(name);
        arrayClazz.setSuperClass(this.loadClass("java/lang/Object"));
        arrayClazz.setInterfaces(new ClassMeta[] {
                this.loadClass("java/lang/Cloneable"),
                this.loadClass("java/io/Serializable")
        });
        arrayClazz.startInit();
        classMap.put(name, arrayClazz);
        return arrayClazz;
    }

    /**
     * 加载非数组的基础类型
     * @param name 类名称
     */
    protected ClassMeta loadNonArrayClass(String name) throws IOException {
        byte[] classBytes = readClass(name);
        ClassMeta clazz = defineClass(classBytes);
        link(clazz);
        if (verboseLoadClass) {
            System.out.println("Loaded Class: " + name);
        }
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
    protected ClassMeta defineClass(byte[] classBytes) {
        ClassMeta clazz = parseClass(classBytes);
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
    protected ClassMeta parseClass(byte[] classBytes) {
        ClassFile classFile = ClassFile.parse(classBytes);
        return ClassMeta.newClass(classFile);
    }

    /**
     * 加载父类
     */
    protected void resolveSuperClass(ClassMeta clazz) {
        if ("java/lang/Object".equals(clazz.getName())) {
            return;
        }
        clazz.setSuperClass(clazz.getLoader().loadClass(clazz.getSuperClassName()));
    }

    /**
     * 加载接口
     */
    protected void resolveInterfaces(ClassMeta clazz) {
        String[] interfaceNames = clazz.getInterfaceNames();
        if (interfaceNames == null) {
            return;
        }
        ClassMeta[] interfaces = new ClassMeta[interfaceNames.length];
        for (int i = 0; i < interfaces.length; i++) {
            interfaces[i] = clazz.getLoader().loadClass(interfaceNames[i]);
        }
        clazz.setInterfaces(interfaces);
    }

    /**
     * 链接阶段
     */
    protected void link(ClassMeta clazz) {
        verify(clazz);
        prepare(clazz);
    }

    /**
     * 验证阶段
     */
    protected void verify(ClassMeta clazz) {
        // TODO: 暂时什么都不做
    }

    /**
     * 准备阶段
     */
    protected void prepare(ClassMeta clazz) {
        calcInstanceFieldSlotIds(clazz);
        calcStaticFieldSlotIds(clazz);
        allocAndInitStaticVars(clazz);
    }

    /**
     * 计算实例字段数量
     */
    private void calcInstanceFieldSlotIds(ClassMeta clazz) {
        int slotId = 0;
        // 父类的字段数量
        if (clazz.getSuperClass() != null) {
            slotId = clazz.getSuperClass().getInstanceSlotCount();
        }
        // 类实例字段数量
        for (FieldMeta field : clazz.getFields()) {
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
    private void calcStaticFieldSlotIds(ClassMeta clazz) {
        int slotId = 0;
        for (FieldMeta field : clazz.getFields()) {
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
    private void allocAndInitStaticVars(ClassMeta clazz) {
        Slot[] slots = new Slot[clazz.getStaticSlotCount()];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = new Slot();
        }
        clazz.setStaticVars(slots);
        for (FieldMeta field : clazz.getFields()) {
            if (field.isStatic() && field.isFinal()) {
                initStaticFinalVars(clazz, field);
            }
        }
    }

    /**
     * 初始化静态常量值
     */
    private void initStaticFinalVars(ClassMeta clazz, FieldMeta field) {
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
                String val = ((StringConstant) constant).value();
                HeapObject stringObj = StringPool.getStringObj(clazz.getLoader(), val);
                vars[slotId].setRef(stringObj);
                break;
            }
            default:
                System.out.println("Unknown field constant: " + field.getDescriptor());
        }
    }

}
