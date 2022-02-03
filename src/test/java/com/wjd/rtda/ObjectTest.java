package com.wjd.rtda;


/**
 * @since 2022/2/2
 */
public class ObjectTest {

    public static int staticVar;
    public int instanceVar;

    public static void main(String[] args) {
        int x = 32768;
        ObjectTest objectTest = new ObjectTest();
        ObjectTest.staticVar = x;
        x = ObjectTest.staticVar;
        objectTest.instanceVar = x + 1;
        x = objectTest.instanceVar;
        Object obj = objectTest;
        if (obj instanceof ObjectTest) {
            objectTest = (ObjectTest) obj;
            System.out.println(objectTest.instanceVar);
        }
    }

}
