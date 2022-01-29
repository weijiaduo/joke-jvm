package com.wjd.classfile.cons;

/**
 * 模块名称常量
 *
 * CONSTANT_Module_info {
 *     u1 tag;
 *     u2 name_index;
 * }
 */
public class ModuleConstantInfo extends NameConstantInfo {

    @Override
    public String toString() {
        return "ModuleConstantInfo{" +
                "index=" + index +
                '}';
    }

}
