package com.wjd.naive.java.lang;

import com.wjd.naive.NativeClass;
import com.wjd.naive.NativeMethod;
import com.wjd.naive.NativeRegistry;
import com.wjd.rtda.Thread;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.ClassMeta;
import com.wjd.rtda.meta.MethodMeta;
import com.wjd.rtda.meta.StackTraceElement;
import com.wjd.rtda.stack.Frame;

import java.util.Arrays;

/**
 * @since 2022/2/14
 */
public class Throwable implements NativeClass {

    static {
        NativeRegistry.registry("java/lang/Throwable", "fillInStackTrace",
                "(I)Ljava/lang/Throwable;", new FillInStackTrace());
    }

    /**
     * private native Throwable fillInStackTrace(int dummy);
     */
    static class FillInStackTrace implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            HeapObject that = frame.getLocalVars().getThis();
            frame.getOperandStack().pushRef(that);
            StackTraceElement[] stacks = createStackTraceElements(that, frame.getThread());
            that.setExtra(stacks);
        }

        /**
         * 生成堆栈信息
         */
        public StackTraceElement[] createStackTraceElements(HeapObject that, Thread thread) {
            // 栈顶两帧正在执行fillInStackTrace（int）和fillInStackTrace（）方法
            // 下面的几帧正在执行异常类的构造函数，具体要跳过多少帧数则要看异常类的继承层次
            int skip = distanceToObject(that.getClazz()) + 2;
            Frame[] frames = thread.getFrames();
            frames = Arrays.copyOfRange(frames, skip, frames.length);
            StackTraceElement[] stacks = new StackTraceElement[frames.length];
            for (int i = 0; i < stacks.length; i++) {
                stacks[i] = createStackTraceElement(frames[i]);
            }
            return stacks;
        }

        /**
         * 类的继承层数
         */
        private int distanceToObject(ClassMeta clazz) {
            int distance = 0;
            for (ClassMeta c = clazz.getSuperClass(); c != null; c = c.getSuperClass()) {
                distance++;
            }
            return distance;
        }

        /**
         * 生成栈帧的堆栈信息
         */
        private StackTraceElement createStackTraceElement(Frame frame) {
            MethodMeta methodMeta = frame.getMethod();
            ClassMeta classMeta = methodMeta.getClazz();
            StackTraceElement stack = new StackTraceElement(classMeta.getSourceFile(),
                    classMeta.getJavaName(),
                    methodMeta.getName(),
                    methodMeta.getLineNumber(frame.getNextPc() - 1));
            return stack;
        }
    }

}
