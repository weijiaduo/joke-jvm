package com.wjd.instructions.comparisons;

/**
 * @since 2022/1/29
 */
public final class CmpUtil {

    private CmpUtil(){}

    public static int cmpInt(int v1, int v2) {
        return Integer.compare(v1, v2);
    }

    public static int cmpLong(long v1, long v2) {
        return Long.compare(v1, v2);
    }

    public static int cmpFloat(float v1, float v2, boolean gFlag) {
        if (v1 > v2) {
            return 1;
        } else if (v1 == v2) {
            return 0;
        } else if (v1 < v2) {
            return -1;
        } else if (gFlag) {
            return 1;
        } else {
            return -1;
        }
    }

    public static int cmpDouble(double v1, double v2, boolean gFlag) {
        if (v1 > v2) {
            return 1;
        } else if (v1 == v2) {
            return 0;
        } else if (v1 < v2) {
            return -1;
        } else if (gFlag) {
            return 1;
        } else {
            return -1;
        }
    }

}
