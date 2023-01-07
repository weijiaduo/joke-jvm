package com.wjd.rtda.meta;

import com.wjd.classfile.attr.SignatureAttributeInfo;
import com.wjd.classfile.MemberInfo;
import com.wjd.classfile.type.Uint16;
import com.wjd.rtda.AccessFlags;

/**
 * 类成员
 * @since 2022/1/30
 */
public abstract class MemberMeta {

    /** 成员所属类 */
    protected ClassMeta classMeta;
    /** 成员的访问标志 */
    protected Uint16 accessFlags;
    /** 成员名称 */
    protected String name;
    /** 成员描述符 */
    protected String descriptor;
    /** 成员签名 */
    protected String signature;

    protected void copyMemberInfo(MemberInfo memberInfo) {
        accessFlags = memberInfo.getAccessFlags();
        name = memberInfo.getName();
        descriptor = memberInfo.getDescriptor();
        SignatureAttributeInfo signatureAttributeInfo = memberInfo.getSignatureAttributeInfo();
        if (signatureAttributeInfo != null) {
            signature = memberInfo.getSignatureAttributeInfo().getSignature();
        }
    }

    public Uint16 getAccessFlags() {
        return accessFlags;
    }

    public String getName() {
        return name;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public String getSignature() {
        return signature;
    }

    public ClassMeta getClassMeta() {
        return classMeta;
    }

    public boolean isStatic() {
        return AccessFlags.isStatic(accessFlags);
    }

    public boolean isFinal() {
        return AccessFlags.isFinal(accessFlags);
    }

    public boolean isAbstract() {
        return AccessFlags.isAbstract(accessFlags);
    }

    public boolean isPrivate() {
        return AccessFlags.isPrivate(accessFlags);
    }

    public boolean isPublic() {
        return AccessFlags.isPublic(accessFlags);
    }

    public boolean isProtected() {
        return AccessFlags.isProtected(accessFlags);
    }

    public boolean isNative() {
        return AccessFlags.isNative(accessFlags);
    }

    /**
     * 是否可以被其他类访问
     * @param other 其他类
     * @return true/false
     */
    public boolean isAccessibleTo(ClassMeta other) {
        // public公开权限
        if (isPublic()) {
            return true;
        }
        // protected保护权限
        if (isProtected()) {
            return classMeta == other ||
                    other.isSubClassOf(classMeta) ||
                    classMeta.getPackageName().equals(other.getPackageName());
        }
        // default包权限
        if (!isPrivate()) {
            return classMeta.getPackageName().equals(other.getPackageName());
        }
        // private私有权限
        return classMeta == other;
    }

}
