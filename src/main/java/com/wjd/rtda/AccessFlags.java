package com.wjd.rtda;

import com.wjd.classfile.type.Uint16;

/**
 * 访问标志
 * @since 2022/1/30
 */
public class AccessFlags {

    public static final int ACCPUBLIC       = 0x0001; //      CcFM_____
    public static final int ACCPRIVATE      = 0x0002; //      _cFM_____
    public static final int ACCPROTECTED    = 0x0004; //      _cFM_____
    public static final int ACCSTATIC       = 0x0008; //      _cFM_____
    public static final int ACCFINAL        = 0x0010; //      CcFMP____
    public static final int ACCSUPER        = 0x0020; //      C________
    public static final int ACCSYNCHRONIZED = 0x0020; //      ___M_____
    public static final int ACCOPEN         = 0x0020; // 9,   _____D___
    public static final int ACCTRANSITIVE   = 0x0020; // 9,   ______R__
    public static final int ACCVOLATILE     = 0x0040; //      __F______
    public static final int ACCBRIDGE       = 0x0040; //      ___M_____
    public static final int ACCSTATICPHASE  = 0x0040; // 9,   ______R__
    public static final int ACCTRANSIENT    = 0x0080; //      __F______
    public static final int ACCVARARGS      = 0x0080; // 5.0  ___M_____
    public static final int ACCNATIVE       = 0x0100; //      ___M_____
    public static final int ACCINTERFACE    = 0x0200; //      Cc_______
    public static final int ACCABSTRACT     = 0x0400; //      Cc_M_____
    public static final int ACCSTRICT       = 0x0800; //      ___M_____
    public static final int ACCSYNTHETIC    = 0x1000; //      CcFMPDRXO
    public static final int ACCANNOTATION   = 0x2000; // 5.0, Cc_______
    public static final int ACCENUM         = 0x4000; // 5.0, CcF______
    public static final int ACCMODULE       = 0x8000; // 9,   C________
    public static final int ACCMANDATED     = 0x8000; // ?,   ____PDRXO

    public static boolean isPublic(Uint16 flags) {
        return (flags.value() & ACCPUBLIC) != 0;
    }

    public static boolean isPrivate(Uint16 flags) {
        return (flags.value() & ACCPRIVATE) != 0;
    }

    public static boolean isProtected(Uint16 flags) {
        return (flags.value() & ACCPROTECTED) != 0;
    }

    public static boolean isStatic(Uint16 flags) {
        return (flags.value() & ACCSTATIC) != 0;
    }

    public static boolean isFinal(Uint16 flags) {
        return (flags.value() & ACCFINAL) != 0;
    }

    public static boolean isSuper(Uint16 flags) {
        return (flags.value() & ACCSUPER) != 0;
    }

    public static boolean isSynchronized(Uint16 flags) {
        return (flags.value() & ACCSYNCHRONIZED) != 0;
    }

    public static boolean isNative(Uint16 flags) {
        return (flags.value() & ACCNATIVE) != 0;
    }

    public static boolean isInterface(Uint16 flags) {
        return (flags.value() & ACCINTERFACE) != 0;
    }

    public static boolean isAbstract(Uint16 flags) {
        return (flags.value() & ACCABSTRACT) != 0;
    }

}
