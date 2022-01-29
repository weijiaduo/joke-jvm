package com.wjd.classfile.attr;

import com.wjd.classfile.ClassReader;
import com.wjd.classfile.type.Uint16;
import com.wjd.classfile.type.Uint32;

/**
 * 模块属性
 *
 * Module_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 module_name_index;
 *     u2 module_flags;
 *     u2 module_version_index;
 *     u2 requires_count;
 *     {   u2 requires_index;
 *         u2 requires_flags;
 *         u2 requires_version_index;
 *     } requires[requires_count];
 *     u2 exports_count;
 *     {   u2 exports_index;
 *         u2 exports_flags;
 *         u2 exports_to_count;
 *         u2 exports_to_index[exports_to_count];
 *     } exports[exports_count];
 *     u2 opens_count;
 *     {   u2 opens_index;
 *         u2 opens_flags;
 *         u2 opens_to_count;
 *         u2 opens_to_index[opens_to_count];
 *     } opens[opens_count];
 *     u2 uses_count;
 *     u2 uses_index[uses_count];
 *     u2 provides_count;
 *     {   u2 provides_index;
 *         u2 provides_with_count;
 *         u2 provides_with_index[provides_with_count];
 *     } provides[provides_count];
 * }
 */
public class ModuleAttributeInfo implements AttributeInfo {

    private Uint32 length;
    private Uint16 moduleNameIndex;
    private Uint16 moduleFlags;
    private Uint16 moduleVersionIndex;
    private Uint16 requiresCount;
    private ModuleRequires[] requires;
    private Uint16 exportsCount;
    private ModuleExports[] exports;
    private Uint16 opensCount;
    private ModuleOpens[] opens;
    private Uint16 usesCount;
    private Uint16[] usesIndex;
    private Uint16 providesCount;
    private ModuleProvides[] provides;

    @Override
    public void readFrom(ClassReader reader) {
        length = reader.readUint32();
        moduleNameIndex = reader.readUint16();
        moduleFlags = reader.readUint16();
        moduleVersionIndex = reader.readUint16();
        requiresCount = reader.readUint16();

        requires = new ModuleRequires[requiresCount.value()];
        for (int i = 0; i < requires.length; i++) {
            requires[i] = readModuleRequires(reader);
        }

        exportsCount = reader.readUint16();
        exports = new ModuleExports[exportsCount.value()];
        for (int i = 0; i < exports.length; i++) {
            exports[i] = readModuleExports(reader);
        }

        opensCount = reader.readUint16();
        opens = new ModuleOpens[opensCount.value()];
        for (int i = 0; i < opens.length; i++) {
            opens[i] = readModuleOpens(reader);
        }

        usesCount = reader.readUint16();
        usesIndex = reader.readUint16s(usesCount);

        providesCount = reader.readUint16();
        provides = new ModuleProvides[providesCount.value()];
        for (int i = 0; i < provides.length; i++) {
            provides[i] = readModuleProvides(reader);
        }
    }

    private ModuleRequires readModuleRequires(ClassReader reader) {
        ModuleRequires req = new ModuleRequires();
        req.setRequiresIndex(reader.readUint16());
        req.setRequiresFlags(reader.readUint16());
        req.setRequiresVersionIndex(reader.readUint16());
        return req;
    }

    private ModuleExports readModuleExports(ClassReader reader) {
        ModuleExports ex = new ModuleExports();
        ex.setExportsIndex(reader.readUint16());
        ex.setExportsFlags(reader.readUint16());
        Uint16 len = reader.readUint16();
        ex.setExportsToIndexTable(reader.readUint16s(len));
        return ex;
    }

    private ModuleOpens readModuleOpens(ClassReader reader) {
        ModuleOpens op = new ModuleOpens();
        op.setOpensIndex(reader.readUint16());
        op.setOpensFlags(reader.readUint16());
        Uint16 len = reader.readUint16();
        op.setOpensToIndexTable(reader.readUint16s(len));
        return op;
    }

    private ModuleProvides readModuleProvides(ClassReader reader) {
        ModuleProvides pro = new ModuleProvides();
        pro.setProvidesIndex(reader.readUint16());
        Uint16 len = reader.readUint16();
        pro.setProvidesWithIndexTable(reader.readUint16s(len));
        return pro;
    }

    public Uint32 getLength() {
        return length;
    }

    public Uint16 getModuleNameIndex() {
        return moduleNameIndex;
    }

    public Uint16 getModuleFlags() {
        return moduleFlags;
    }

    public Uint16 getModuleVersionIndex() {
        return moduleVersionIndex;
    }

    public Uint16 getRequiresCount() {
        return requiresCount;
    }

    public ModuleRequires[] getRequires() {
        return requires;
    }

    public Uint16 getExportsCount() {
        return exportsCount;
    }

    public ModuleExports[] getExports() {
        return exports;
    }

    public Uint16 getOpensCount() {
        return opensCount;
    }

    public ModuleOpens[] getOpens() {
        return opens;
    }

    public Uint16 getUsesCount() {
        return usesCount;
    }

    public Uint16[] getUsesIndex() {
        return usesIndex;
    }

    public Uint16 getProvidesCount() {
        return providesCount;
    }

    public ModuleProvides[] getProvides() {
        return provides;
    }
}
