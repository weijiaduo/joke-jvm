package com.wjd.rtda.meta;

import com.wjd.classfile.type.Uint16;
import com.wjd.rtda.AccessFlags;

/**
 * @since 2022/2/15
 */
public class ShimClassMeta extends ClassMeta {

    public static ShimClassMeta instance = new ShimClassMeta();

    public static ShimClassMeta getInstance() {
        return instance;
    }

    private MethodMeta returnMethod;
    private MethodMeta bootStrapMethod;

    public ShimClassMeta() {
        name = "~shim";
        methods = new MethodMeta[] {
                getReturnMethod(),
                getBootStrapMethod()
        };
    }

    /**
     * 空返回方法
     */
    public MethodMeta getReturnMethod() {
        if (returnMethod == null) {
            returnMethod = new MethodMeta();
            returnMethod.accessFlags = new Uint16(AccessFlags.ACCSTATIC);
            returnMethod.name = "<return>";
            returnMethod.clazz = this;
            returnMethod.codes = new byte[] {(byte) 0xb1}; // return
        }
        return returnMethod;
    }

    /**
     * 启动方法
     */
    public MethodMeta getBootStrapMethod() {
        if (bootStrapMethod == null) {
            bootStrapMethod = new MethodMeta();
            bootStrapMethod.accessFlags = new Uint16(AccessFlags.ACCSTATIC);
            bootStrapMethod.name = "<bootstrap>";
            bootStrapMethod.clazz = this;
            bootStrapMethod.codes = new byte[] {(byte) 0xff, (byte) 0xb1}; // boostrap, return
            bootStrapMethod.maxLocals = 8;
            bootStrapMethod.maxStacks = 8;
        }
        return bootStrapMethod;
    }
}
