package com.wjd.rtda.heap;

import com.wjd.classfile.ClassFile;
import com.wjd.classfile.type.Uint16;
import com.wjd.rtda.Slot;
import com.wjd.rtda.heap.member.Field;
import com.wjd.rtda.heap.member.Method;

/**
 * 类
 * @since 2022/1/30
 */
public class Class {

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

    public String getName() {
        return name;
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

    public String getPackageName() {
        String packageName = "";
        int index = name.lastIndexOf("/");
        if (index > 0) {
            packageName = name.substring(0, index);
        }
        return packageName;
    }

    public boolean isSubClassOf(Class parent) {
        for (Class p = superClass; p != null; p = p.superClass) {
            if (p == parent) {
                return true;
            }
        }
        return false;
    }
}
