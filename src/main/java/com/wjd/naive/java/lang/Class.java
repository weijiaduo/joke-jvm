package com.wjd.naive.java.lang;

import com.wjd.instructions.references.InitClass;
import com.wjd.naive.NativeClass;
import com.wjd.naive.NativeMethod;
import com.wjd.naive.NativeRegistry;
import com.wjd.rtda.Slot;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.*;
import com.wjd.rtda.stack.Frame;

/**
 * @since 2022/2/12
 */
public class Class implements NativeClass {

    static {
        NativeRegistry.registry("java/lang/Class", "getPrimitiveClass",
                "(Ljava/lang/String;)Ljava/lang/Class;", new GetPrimitiveClass());
        NativeRegistry.registry("java/lang/Class", "getName0",
                "()Ljava/lang/String;", new GetName0());
        NativeRegistry.registry("java/lang/Class", "desiredAssertionStatus0",
                "(Ljava/lang/Class;)Z", new DesiredAssertionStatus0());
        NativeRegistry.registry("java/lang/Class", "getClassLoader0",
                "()Ljava/lang/ClassLoader;", new GetClassLoader0());
        NativeRegistry.registry("java/lang/Class", "getDeclaredFields0",
                "(Z)[Ljava/lang/reflect/Field;", new GetDeclaredFields0());
        NativeRegistry.registry("java/lang/Class", "isPrimitive",
                "()Z", new IsPrimitive());
        NativeRegistry.registry("java/lang/Class", "forName0",
                "(Ljava/lang/String;ZLjava/lang/ClassLoader;Ljava/lang/Class;)Ljava/lang/Class;",
                new ForName0());
        NativeRegistry.registry("java/lang/Class", "isInterface",
                "()Z", new IsInterface());
        NativeRegistry.registry("java/lang/Class", "getDeclaredConstructors0",
                "(Z)[Ljava/lang/reflect/Constructor;", new GetDeclaredConstructors0());
        NativeRegistry.registry("java/lang/Class", "getModifiers",
                "()I", new GetModifiers());
        NativeRegistry.registry("java/lang/Class", "getSuperclass",
                "()Ljava/lang/Class;", new GetSuperclass());
    }


    /**
     * static native Class<?> getPrimitiveClass(String name);
     */
    static class GetPrimitiveClass implements NativeMethod {
        @Override
        public void execute(Frame frame) {
            HeapObject nameObj = frame.getLocalVars().getRef(0);
            java.lang.String name = StringPool.getRawString(nameObj);
            ClassMetaLoader loader = frame.getMethod().getClazz().getLoader();
            HeapObject jClass = loader.loadClass(name).getjClass();
            frame.getOperandStack().pushRef(jClass);
        }
    }

    /**
     * private native String getName0();
     */
    static class GetName0 implements NativeMethod {
        @Override
        public void execute(Frame frame) {
            HeapObject that = frame.getLocalVars().getThis();
            // 注意这里取的是Class<T>中的T
            ClassMeta clazz = (ClassMeta) that.getExtra();
            java.lang.String name = clazz.getJavaName();
            HeapObject nameObj = StringPool.getStringObj(clazz.getLoader(), name);
            frame.getOperandStack().pushRef(nameObj);
        }
    }

    /**
     * private static native boolean desiredAssertionStatus0(Class<?> clazz);
     */
    static class DesiredAssertionStatus0 implements NativeMethod {
        @Override
        public void execute(Frame frame) {
            frame.getOperandStack().pushBoolean(false);
        }
    }

    /**
     * native ClassLoader getClassLoader0();
     */
    static class GetClassLoader0 implements NativeMethod {
        @Override
        public void execute(Frame frame) {
            frame.getOperandStack().pushRef(null);
        }
    }

    /**
     * private native Field[] getDeclaredFields0(boolean publicOnly);
     */
    static class GetDeclaredFields0 implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            ClassMetaLoader loader = frame.getMethod().getClazz().getLoader();

            HeapObject classObj = frame.getLocalVars().getThis();
            boolean publicOnly = frame.getLocalVars().getBoolean(1);

            // 注意这里取的是Class<T>中的T
            ClassMeta clazz = (ClassMeta) classObj.getExtra();
            FieldMeta[] fields = clazz.getFields(publicOnly);
            int fieldCount = fields.length;

            ClassMeta fieldClass = clazz.getLoader().loadClass("java/lang/reflect/Field");
            ClassMeta fieldArrClass = fieldClass.getArrayClass();
            HeapObject fieldArr = fieldArrClass.newArray(fieldCount);

            frame.getOperandStack().pushRef(fieldArr);

            if (fieldCount > 0) {
                HeapObject[] fieldObjs = fieldArr.getRefs();
                java.lang.String fieldConstructorDescriptor = "" +
                        "(Ljava/lang/Class;" +
                        "Ljava/lang/String;" +
                        "Ljava/lang/Class;" +
                        "II" +
                        "Ljava/lang/String;" +
                        "[B)V";
                MethodMeta fieldConstructor = fieldClass.getConstructor(fieldConstructorDescriptor);
                for (int i = 0; i < fieldObjs.length; i++) {
                    fieldObjs[i] = fieldClass.newObject();
                    fieldObjs[i].setExtra(fields[i]);

                    // this
                    Slot fieldSlot = new Slot();
                    fieldSlot.setRef(fieldObjs[i]);
                    // class
                    Slot classObjSlot = new Slot();
                    classObjSlot.setRef(classObj);
                    // name
                    HeapObject nameObj = StringPool.getStringObj(clazz.getLoader(), fields[i].getName());
                    Slot nameSlot = new Slot();
                    nameSlot.setRef(nameObj);
                    // type
                    Slot typeSlot = new Slot();
                    typeSlot.setRef(fields[i].getType().getjClass());
                    // acc
                    Slot accSlot = new Slot();
                    Slot.setInt(accSlot, fields[i].getAccessFlags().value());
                    // slot id
                    Slot idSlot = new Slot();
                    Slot.setInt(idSlot, fields[i].getSlotId());
                    // signature
                    Slot signatureSlot = null;
                    java.lang.String signature = fields[i].getSignature();
                    if (signature != null) {
                        HeapObject signatureObj = StringPool.getStringObj(loader, signature);
                        signatureSlot = new Slot();
                        signatureSlot.setRef(signatureObj);
                    }
                    // annotation
                    Slot annotationsSlot = null;
                    frame.getThread().invokeMethodWithShim(fieldConstructor, new Slot[] {
                            fieldSlot,      // this
                            classObjSlot,   // class
                            nameSlot,       // name
                            typeSlot,       // type
                            accSlot,        // modifiers
                            idSlot,         // slot id
                            signatureSlot,  // signature
                            annotationsSlot // annotations
                    });
                }
            }
        }
    }

    /**
     * public native boolean isPrimitive();
     */
    static class IsPrimitive implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            HeapObject that = frame.getLocalVars().getThis();
            // 注意这里取的是Class<T>中的T
            ClassMeta clazz = (ClassMeta) that.getExtra();
            frame.getOperandStack().pushBoolean(clazz.isPrimitive());
        }
    }

    /**
     * private static native Class<?> forName0(String name, boolean initialize,
     *                                          ClassLoader loader,
     *                                          Class<?> caller)
     */
    static class ForName0 implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            HeapObject nameObj = frame.getLocalVars().getRef(0);
            boolean initialize = frame.getLocalVars().getBoolean(1);

            java.lang.String name = StringPool.getRawString(nameObj);
            java.lang.String className = name.replaceAll("\\.", "/");

            ClassMetaLoader loader = frame.getMethod().getClazz().getLoader();
            ClassMeta clazz = loader.loadClass(className);
            HeapObject jClass = clazz.getjClass();

            if (initialize && !clazz.isInitStarted()) {
                frame.getThread().revertNextPc();
                InitClass.initClass(frame.getThread(), clazz);
            } else {
                frame.getOperandStack().pushRef(jClass);
            }
        }
    }

    /**
     * public native boolean isInterface();
     */
    static class IsInterface implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            HeapObject that = frame.getLocalVars().getThis();
            // 注意这里取的是Class<T>中的T
            ClassMeta clazz = (ClassMeta) that.getExtra();
            frame.getOperandStack().pushBoolean(clazz.isInterface());
        }
    }

    /**
     * private native Constructor<T>[] getDeclaredConstructors0(boolean publicOnly);
     */
    static class GetDeclaredConstructors0 implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            ClassMetaLoader loader = frame.getMethod().getClazz().getLoader();

            HeapObject classObj = frame.getLocalVars().getThis();
            boolean publicOnly = frame.getLocalVars().getBoolean(1);

            // 注意这里取的是Class<T>中的T
            ClassMeta clazz = (ClassMeta) classObj.getExtra();
            MethodMeta[] constructorMetas = clazz.getConstructors(publicOnly);
            int count = constructorMetas.length;

            ClassMeta constructorClass = clazz.getLoader().loadClass("java/lang/reflect/Constructor");
            ClassMeta constructorArrClass = constructorClass.getArrayClass();
            HeapObject constructorArr = constructorArrClass.newArray(count);

            frame.getOperandStack().pushRef(constructorArr);

            if (count > 0) {
                HeapObject[] constructorObjs = constructorArr.getRefs();
                java.lang.String constructorDescriptor = "" +
                        "(Ljava/lang/Class;" +
                        "[Ljava/lang/Class;" +
                        "[Ljava/lang/Class;" +
                        "II" +
                        "Ljava/lang/String;" +
                        "[B[B)V";
                MethodMeta constructorMethod = constructorClass.getConstructor(constructorDescriptor);
                for (int i = 0; i < constructorObjs.length; i++) {
                    MethodMeta constructorMeta = constructorMetas[i];
                    constructorObjs[i] = constructorClass.newObject();
                    constructorObjs[i].setExtra(constructorMeta);

                    // this
                    Slot constructorSlot = new Slot();
                    constructorSlot.setRef(constructorObjs[i]);
                    // class
                    Slot classObjSlot = new Slot();
                    classObjSlot.setRef(classObj);
                    // 参数数组
                    HeapObject paramArr = constructorMeta.getParameterTypeArr();
                    Slot paramSlot = new Slot();
                    paramSlot.setRef(paramArr);
                    // 异常类数组
                    HeapObject exceptionArr = constructorMeta.getExceptionTypeArr();
                    Slot exSlot = new Slot();
                    exSlot.setRef(exceptionArr);
                    // 访问标记
                    Slot accSlot = new Slot();
                    Slot.setInt(accSlot, constructorMeta.getAccessFlags().value());
                    // 插槽
                    Slot slot = new Slot();
                    Slot.setInt(slot, 0);
                    // 签名
                    Slot signatureSlot = null;
                    java.lang.String signature = constructorMeta.getSignature();
                    if (signature != null) {
                        HeapObject signatureObj = StringPool.getStringObj(loader, signature);
                        signatureSlot = new Slot();
                        signatureSlot.setRef(signatureObj);
                    }
                    // 注解
                    Slot annotationsSlot = null;
                    // 注解参数
                    Slot paramAnnotationSlot = null;
                    frame.getThread().invokeMethodWithShim(constructorMethod, new Slot[] {
                            constructorSlot,     // this
                            classObjSlot,       // class
                            paramSlot,          // name
                            exSlot,             // modifiers
                            accSlot,            // modifiers
                            slot,               // slot id
                            signatureSlot,      // signature
                            annotationsSlot,    // annotations
                            paramAnnotationSlot
                    });
                }
            }
        }
    }

    /**
     * public native int getModifiers();
     */
    static class GetModifiers implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            HeapObject that = frame.getLocalVars().getThis();
            // 注意这里取的是Class<T>中的T
            ClassMeta classMeta = (ClassMeta) that.getExtra();
            frame.getOperandStack().pushInt(classMeta.getAccessFlags().value());
        }
    }

    /**
     * public native Class<? super T> getSuperclass();
     */
    static class GetSuperclass implements NativeMethod {
        @Override
        public void execute(Frame frame) throws Exception {
            HeapObject that = frame.getLocalVars().getThis();
            // 注意这里取的是Class<T>中的T
            ClassMeta classMeta = (ClassMeta) that.getExtra();
            ClassMeta superClassMeta = classMeta.getSuperClass();
            if (superClassMeta != null) {
                frame.getOperandStack().pushRef(superClassMeta.getjClass());
            } else {
                frame.getOperandStack().pushRef(null);
            }
        }
    }

}
