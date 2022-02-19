package com.wjd.instructions.references;

import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.Thread;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.MethodMeta;

/**
 * @since 2022/2/10
 */
public class InitClass {

    public static void initClass(Thread thread, ClassMeta clazz) {
        clazz.startInit();
        // 调用类的初始化方法
        scheduleClinit(thread, clazz);
        // 调用父类的初始化方法
        initSuperClass(thread, clazz);
    }

    private static void scheduleClinit(Thread thread, ClassMeta clazz) {
        MethodMeta clinit = clazz.getClinitMethod();
        if (clinit != null) {
            Frame newFrame = thread.newFrame(clinit);
            thread.pushFrame(newFrame);
        }
    }

    private static void initSuperClass(Thread thread, ClassMeta clazz) {
        if (!clazz.isInterface()) {
            ClassMeta superClazz = clazz.getSuperClass();
            if (superClazz != null && !superClazz.isInitStarted()) {
                initClass(thread, superClazz);
            }
        }
    }

}
