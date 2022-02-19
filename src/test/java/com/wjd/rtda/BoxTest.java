package com.wjd.rtda;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 2022/2/13
 */
public class BoxTest {

    public static void main(String[] args) {
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        list.add(3L);
        System.out.println(list.toString());
        for (Long x : list) {
            testBox(x);
        }
    }

    private static void testBox(long x) {
        System.out.println(x);
    }

}
