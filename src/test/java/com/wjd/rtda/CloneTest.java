package com.wjd.rtda;

/**
 * @since 2022/2/13
 */
public class CloneTest implements Cloneable {

    private double pi = 3.14;
    private Object obj = null;

    @Override
    public CloneTest clone() {
        try {
            CloneTest cloneTest = (CloneTest) super.clone();
            if (cloneTest.obj != null) {
                cloneTest.obj = ((CloneTest) cloneTest.obj).clone();
            }
            return cloneTest;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        CloneTest obj1 = new CloneTest();
        obj1.obj = new CloneTest();
        CloneTest obj2 = obj1.clone();
        obj1.pi = 3.1415926;
        System.out.println(obj1.pi);
        System.out.println(obj2.pi);
        System.out.println(obj1.obj == obj2.obj);
    }
}
