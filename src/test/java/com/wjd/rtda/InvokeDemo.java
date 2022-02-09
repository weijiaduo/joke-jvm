package com.wjd.rtda;

/**
 * @since 2022/2/9
 */
public class InvokeDemo implements Runnable {

    public static void main(String[] args) {
        new InvokeDemo().test();
    }

    public void test() {
        InvokeDemo.staticMethod();                 // invokestatic
        InvokeDemo invokeDemo = new InvokeDemo();  // invokespecial
        invokeDemo.instanceMethod();               // invokespecial
        super.equals(null);                        // invokespecial
        this.run();                                // invokevirtual
        ((Runnable) invokeDemo).run();             // invokeinterafce
    }

    public static void staticMethod(){}

    private void instanceMethod(){}

    @Override
    public void run() {
    }
}
