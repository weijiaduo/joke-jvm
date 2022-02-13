package com.wjd.rtda;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 2022/2/13
 */
public class BoxTest {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(list.toString());
        for (int x : list) {
            System.out.println(x);
        }
    }

}
