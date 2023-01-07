package com.wjd.instructions.references;

import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.Thread;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.MethodMeta;

/**
 * @since 2022/2/10
 */
public class InitClass {

    public static void initClass(Thread thread, ClassMeta classMeta) {
        classMeta.startInit();
        // 调用类的初始化方法
        scheduleClinit(thread, classMeta);
        // 调用父类的初始化方法
        initSuperClass(thread, classMeta);
    }

    private static void scheduleClinit(Thread thread, ClassMeta classMeta) {
        MethodMeta clinit = classMeta.getClinitMethod();
        if (clinit != null) {
            Frame newFrame = thread.newFrame(clinit);
            thread.pushFrame(newFrame);
        }
    }

    private static void initSuperClass(Thread thread, ClassMeta classMeta) {
        if (!classMeta.isInterface()) {
            ClassMeta superClassMeta = classMeta.getSuperClass();
            if (superClassMeta != null && !superClassMeta.isInitStarted()) {
                initClass(thread, superClassMeta);
            }
        }
    }

}
