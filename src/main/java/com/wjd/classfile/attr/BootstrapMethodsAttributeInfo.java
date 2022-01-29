package com.wjd.classfile.attr;

import com.wjd.classfile.ClassReader;
import com.wjd.classfile.type.Uint16;
import com.wjd.classfile.type.Uint32;

/**
 * 启动方法属性
 *
 * BootstrapMethods_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 num_bootstrap_methods;
 *     {   u2 bootstrap_method_ref;
 *         u2 num_bootstrap_arguments;
 *         u2 bootstrap_arguments[num_bootstrap_arguments];
 *     } bootstrap_methods[num_bootstrap_methods];
 * }
 */
public class BootstrapMethodsAttributeInfo implements AttributeInfo {

    private Uint32 length;
    private Uint16 numOfBootstrapMethods;
    private BootstrapMethod[] bootstrapMethods;

    @Override
    public void readFrom(ClassReader reader) {
        length = reader.readUint32();
        numOfBootstrapMethods = reader.readUint16();
        bootstrapMethods = new BootstrapMethod[numOfBootstrapMethods.value()];
        for (int i = 0; i < bootstrapMethods.length; i++) {
            bootstrapMethods[i] = readBootstrapMethod(reader);
        }
    }

    private BootstrapMethod readBootstrapMethod(ClassReader reader) {
        BootstrapMethod bootstrapMethod = new BootstrapMethod();
        bootstrapMethod.setBootstrapMethodRef(reader.readUint16());
        Uint16 argsLength = reader.readUint16();
        Uint16[] arguments = reader.readUint16s(argsLength);
        bootstrapMethod.setBootstrapArguments(arguments);
        return bootstrapMethod;
    }

    public Uint32 getLength() {
        return length;
    }

    public Uint16 getNumOfBootstrapMethods() {
        return numOfBootstrapMethods;
    }

    public BootstrapMethod[] getBootstrapMethods() {
        return bootstrapMethods;
    }
}
