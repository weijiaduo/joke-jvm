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

    protected Uint16 accessFlags;
    protected String name;
    protected String descriptor;
    protected String signature;
    protected ClassMeta clazz;

    public void copyMemberInfo(MemberInfo memberInfo) {
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

    public ClassMeta getClazz() {
        return clazz;
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

    public boolean isAccessibleTo(ClassMeta other) {
        // 公开权限
        if (isPublic()) {
            return true;
        }
        // 保护权限
        if (isProtected()) {
            return clazz == other ||
                    other.isSubClassOf(clazz) ||
                    clazz.getPackageName().equals(other.getPackageName());
        }
        // 包权限
        if (!isPrivate()) {
            return clazz.getPackageName().equals(other.getPackageName());
        }
        // 私有权限
        return clazz == other;
    }

}
