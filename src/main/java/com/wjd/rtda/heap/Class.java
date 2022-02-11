package com.wjd.rtda.heap;

import com.wjd.classfile.ClassFile;
import com.wjd.classfile.type.Uint16;
import com.wjd.classfile.type.Uint8;
import com.wjd.rtda.Slot;
import com.wjd.rtda.heap.member.Field;
import com.wjd.rtda.heap.member.Method;
import sun.awt.geom.AreaOp;

import java.util.HashMap;
import java.util.Map;

/**
 * 类
 * @since 2022/1/30
 */
public class Class {

    public static Map<String, String> primitiveTypes;
    {
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

    /** 访问标志 */
    private Uint16 accessFlags;
    /** 类完全限定名 */
    private String name;
    /** 父类完全限定名 */
    private String superClassName;
    /** 接口完全限定名 */
    private String[] interfaceNames;
    /** 常量池 */
    private ConstantPool constantPool;
    /** 字段 */
    private Field[] fields;
    /** 方法 */
    private Method[] methods;
    /** 类加载器 */
    private ClassLoader loader;
    /** 父类引用 */
    private Class superClass;
    /** 接口引用 */
    private Class[] interfaces;
    /** 实例变量数量 */
    private int instanceSlotCount;
    /** 静态变量数量 */
    private int staticSlotCount;
    /** 静态变量 */
    private Slot[] staticVars;

    /** 类是否已初始化 */
    private boolean initStarted;

    public static Class newClass(ClassFile classFile) {
        Class clazz = new Class();
        clazz.accessFlags = classFile.getAccessFlags();
        clazz.name = classFile.getClassName();
        clazz.superClassName = classFile.getSuperClassName();
        clazz.interfaceNames = classFile.getInterfaceNames();
        clazz.constantPool = ConstantPool.newConstantPool(clazz, classFile.getConstantPool());
        clazz.fields = Field.newFields(clazz, classFile.getFields());
        clazz.methods = Method.newMethods(clazz, classFile.getMethods());
        return clazz;
    }

    public void setAccessFlags(Uint16 accessFlags) {
        this.accessFlags = accessFlags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public String[] getInterfaceNames() {
        return interfaceNames;
    }

    public ConstantPool getConstantPool() {
        return constantPool;
    }

    public Field[] getFields() {
        return fields;
    }

    public Method[] getMethods() {
        return methods;
    }

    public ClassLoader getLoader() {
        return loader;
    }

    public void setLoader(ClassLoader loader) {
        this.loader = loader;
    }

    public Class getSuperClass() {
        return superClass;
    }

    public void setSuperClass(Class superClass) {
        this.superClass = superClass;
    }

    public Class[] getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(Class[] interfaces) {
        this.interfaces = interfaces;
    }

    public void setInstanceSlotCount(int instanceSlotCount) {
        this.instanceSlotCount = instanceSlotCount;
    }

    public void setStaticSlotCount(int staticSlotCount) {
        this.staticSlotCount = staticSlotCount;
    }

    public int getInstanceSlotCount() {
        return instanceSlotCount;
    }

    public int getStaticSlotCount() {
        return staticSlotCount;
    }

    public Slot[] getStaticVars() {
        return staticVars;
    }

    public void setStaticVars(Slot[] staticVars) {
        this.staticVars = staticVars;
    }

    public boolean isInitStarted() {
        return initStarted;
    }

    public void startInit() {
        this.initStarted = true;
    }

    public boolean isAccessibleTo(Class other) {
        // 公开权限/包权限
        return isPublic() || getPackageName().equals(other.getPackageName());
    }

    public boolean isPublic() {
        return AccessFlags.isPublic(accessFlags);
    }

    public boolean isProtected() {
        return AccessFlags.isProtected(accessFlags);
    }

    public boolean isPrivate() {
        return AccessFlags.isPrivate(accessFlags);
    }

    public boolean isInterface() {
        return AccessFlags.isInterface(accessFlags);
    }

    public boolean isAbstract() {
        return AccessFlags.isAbstract(accessFlags);
    }

    public boolean isSuper() {
        return AccessFlags.isSuper(accessFlags);
    }

    public String getPackageName() {
        String packageName = "";
        System.out.println("package name: " + name);
        int index = name.lastIndexOf("/");
        if (index > 0) {
            packageName = name.substring(0, index);
        }
        return packageName;
    }

    public boolean isSuperClassOf(Class child) {
        return child.isSubClassOf(this);
    }

    public boolean isSubClassOf(Class parent) {
        for (Class p = superClass; p != null; p = p.superClass) {
            if (p == parent) {
                return true;
            }
        }
        return false;
    }

    public boolean isSuperInterfaceOf(Class child) {
        return child.isSubInterfaceOf(this);
    }

    public boolean isSubInterfaceOf(Class parent) {
        for (Class i : getInterfaces()) {
            if (i == parent || i.isSubInterfaceOf(parent)) {
                return true;
            }
        }
        return false;
    }

    public boolean isImplements(Class parent) {
        for (Class c = this; c != null; c = c.superClass) {
            for (Class i : c.getInterfaces()) {
                if (i == parent || i.isSubInterfaceOf(parent)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isAssignableFrom(Class sub) {
        if (this == sub) {
            return true;
        }
        Class s = sub;
        Class t = this;
        if (!s.isArray()) {
            // 非数组类型
            if (!s.isInterface()) {
                if (!t.isInterface()) {
                    return s.isSubClassOf(t);
                } else {
                    return s.isImplements(t);
                }
            } else {
                if (!t.isInterface()) {
                    // 接口可以转成 Object
                    return t.isJlObject();
                } else {
                    return t.isSuperInterfaceOf(s);
                }
            }

        } else {
            // 数组类型
            if (!t.isArray()) {
                // 数组可以转成 Object/Cloneable/Serializable
                if (!t.isInterface()) {
                    return t.isJlObject();
                } else {
                    return t.isJlCloneable() || t.isJioSerializable();
                }
            } else {
                // 都是数组类型
                Class sc = s.getComponentClass();
                Class tc = t.getComponentClass();
                return sc == tc || tc.isAssignableFrom(sc);
            }
        }
    }

    public boolean isJlObject() {
        return this == loader.loadClass("java/lang/Object");
    }

    public boolean isJlCloneable() {
        return this == loader.loadClass("java/lang/Cloneable");
    }

    public boolean isJioSerializable() {
        return this == loader.loadClass("java/io/Serializable");
    }

    /**
     * 获取类文件中的main方法
     */
    public Method getMainMethod() {
        return getStaticMethod("main", "([Ljava/lang/String;)V");
    }

    public Method getClinitMethod() {
        return getStaticMethod("<clinit>", "()V");
    }

    /**
     * 获取静态方法
     */
    public Method getStaticMethod(String name, String descriptor) {
        for (Method m : getMethods())
            if (name.equals(m.getName()) && descriptor.equals(m.getDescriptor())) {
                return m;
            }
        return null;
    }

    public boolean isArray() {
        return name.charAt(0) == '[';
    }

    /**
     * 获取基本类型的数组类型
     */
    public static Class getPrimitiveArrayClass(ClassLoader loader, Uint8 atype) {
        int val = atype.value();
        switch (val) {
            case 4:
                return loader.loadClass("[Z");
            case 5:
                return loader.loadClass("[C");
            case 6:
                return loader.loadClass("[F");
            case 7:
                return loader.loadClass("[D");
            case 8:
                return loader.loadClass("[B");
            case 9:
                return loader.loadClass("[S");
            case 10:
                return loader.loadClass("[I");
            case 11:
                return loader.loadClass("[J");
            default:
                throw new IllegalArgumentException("Invalid atype: " + val);
        }
    }

    /**
     * 获取当前类型对应的数组类型
     */
    public Class getArrayClass() {
        String arrayClassName = getArrayClassName(name);
        return loader.loadClass(arrayClassName);
    }

    private String getArrayClassName(String className) {
        return "[" + toDescriptor(className);
    }

    private String toDescriptor(String className) {
        // 数组类型
        if (className.charAt(0) == '[') {
            return className;
        }
        // 基本类型
        if (primitiveTypes.containsKey(className)) {
            return primitiveTypes.get(className);
        }
        // 引用类型
        return "L" + className + ";";
    }

    /**
     * 获取数组类型的元素类型
     */
    public Class getComponentClass() {
        String componentClassName = getComponentClassName(name);
        return loader.loadClass(componentClassName);
    }

    private String getComponentClassName(String className) {
        if (className.charAt(0) == '[') {
            String componentTypeDescriptor = className.substring(1);
            return toClassName(componentTypeDescriptor);
        }
        throw new IllegalArgumentException("Not Array: " + className);
    }

    private String toClassName(String descriptor) {
        // 数组类型
        if (descriptor.charAt(0) == '[') {
            return descriptor;
        }
        // 引用类型
        if (descriptor.charAt(0) == 'L') {
            return descriptor.substring(1, descriptor.length() - 1);
        }
        // 基本类型
        for (String className : primitiveTypes.keySet()) {
            if (descriptor.equals(primitiveTypes.get(className))) {
                return className;
            }
        }
        throw new IllegalArgumentException("Invalid descriptor: " + descriptor);
    }

    public HeapObject newObject() {
        return HeapObject.newObject(this);
    }

    public HeapObject newArray(int count) {
        if (!isArray()) {
            throw new IllegalStateException("Not array class: " + name);
        }
        return HeapObject.newArray(this, makeArray(count));
    }

    private Object makeArray(int count) {
        switch (name) {
            case "[Z":
                return new boolean[count];
            case "[B":
                return new byte[count];
            case "[C":
                return new char[count];
            case "[S":
                return new short[count];
            case "[I":
                return new int[count];
            case "[J":
                return new long[count];
            case "[F":
                return new float[count];
            case "[D":
                return new double[count];
            default:
                return new HeapObject[count];
        }
    }

    public Field getField(String name, String descriptor, boolean isStatic) {
        for (Class c = this; c != null; c = c.getSuperClass()) {
            for (Field field : c.getFields()) {
                if (field.isStatic() == isStatic &&
                        field.getName().equals(name) &&
                        field.getDescriptor().equals(descriptor)) {
                    return field;
                }
            }
        }
        return null;
    }
}
