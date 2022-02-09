package com.wjd.instructions.references;

import com.wjd.rtda.Frame;
import com.wjd.rtda.Thread;
import com.wjd.rtda.heap.Class;
import com.wjd.rtda.heap.member.Method;

/**
 * @since 2022/2/10
 */
public class InitClass {

    public static void initClass(Thread thread, Class clazz) {
        clazz.startInit();
        // 调用类的初始化方法
        scheduleClinit(thread, clazz);
        // 调用父类的初始化方法
        initSuperClass(thread, clazz);
    }

    private static void scheduleClinit(Thread thread, Class clazz) {
        Method clinit = clazz.getClinitMethod();
        if (clinit != null) {
            Frame newFrame = thread.newFrame(clinit);
            thread.pushFrame(newFrame);
        }
    }

    private static void initSuperClass(Thread thread, Class clazz) {
        if (!clazz.isInterface()) {
            Class superClass = clazz.getSuperClass();
            if (superClass != null && !superClass.isInitStarted()) {
                initClass(thread, superClass);
            }
        }
    }

}
