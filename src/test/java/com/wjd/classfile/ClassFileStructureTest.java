package com.wjd.classfile;

public class ClassFileStructureTest {

    private byte bt = 1;
    private boolean b = false;
    private char c = 'A';
    private short s = 2;
    private int i = 3;
    private long l = 4L;
    private float f = 5.0F;
    private double d = 6.0;
    private String str = "test";

    public static void main(String[] args) {
        short s = -10;
        int i = s;
        int ii = s & 0xFFFF;
        long ss = 0L;
        long si = ss | i;
        for (int j = 0; j < 4; j++) {
        }
        System.out.println("Class File Test" + i + " " + ii + " " + si);
    }

}
