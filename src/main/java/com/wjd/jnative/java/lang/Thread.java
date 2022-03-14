package com.wjd.jnative.java.lang;

import com.wjd.jnative.NativeClass;
import com.wjd.jnative.NativeMethod;
import com.wjd.jnative.NativeRegistry;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.MethodMeta;
import com.wjd.rtda.stack.Frame;

/**
 * @since 2022/2/15
 */
public class Thread implements NativeClass {

    static {
        NativeRegistry.registry("java/lang/Thread", "currentThread",
                "()Ljava/lang/Thread;", new CurrentThread());
        NativeRegistry.registry("java/lang/Thread", "setPriority0",
                "(I)V", new SetPriority0());
        NativeRegistry.registry("java/lang/Thread", "isAlive",
                "()Z", new IsAlive());
        NativeRegistry.registry("java/lang/Thread", "start0",
                "()V", new Start0());
    }

    /**
     * public static native Thread currentThread();
     */
    static class CurrentThread implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            frame.getOpStack().pushRef(frame.getThread().getJThread());
        }
    }

    /**
     * private native void setPriority0(int newPriority);
     */
    static class SetPriority0 implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
        }
    }

    /**
     * public final native boolean isAlive();
     */
    static class IsAlive implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            HeapObject that = frame.getLocalVars().getThis();
            java.lang.Object extra = that.getExtra();
            boolean isAlive = extra instanceof com.wjd.rtda.Thread;
            frame.getOpStack().pushBoolean(isAlive);
        }
    }

    /**
     * private native void start0();
     */
    static class Start0 implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            HeapObject that = frame.getLocalVars().getThis();
            com.wjd.rtda.Thread newThread = new com.wjd.rtda.Thread(frame.getThread().getJvmOptions());
            newThread.setJThread(that);

            MethodMeta runMethod = that.getClazz().getInstanceMethod("run", "()V");
            Frame newFrame = newThread.newFrame(runMethod);
            newFrame.getLocalVars().setRef(0, that);
            newThread.pushFrame(newFrame);

            that.setExtra(newThread);

            // TODO: 多线程暂未实现
            // new Interpreter().interpret(newThread, false);
        }
    }

}
