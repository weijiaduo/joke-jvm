package com.wjd.jnative;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

/**
 * @since 2022/2/18
 */
public class Memory {

    private static Map<Long, byte[]> memory = new LinkedHashMap<>();
    private static long nextAddress = 1;

    /**
     * 分配指定字节的内存
     * @param size 字节数量
     * @return 地址
     */
    public static Long allocate(long size) {
        // FIXME: java语法限制数组只能用int
        int fixSize = (int) size;
        byte[] mem = new byte[fixSize];
        long address = nextAddress;
        memory.put(address, mem);
        nextAddress += fixSize;
        return address;
    }

    /**
     * 释放指定地址内存
     * @param address 地址
     */
    public static void freeMemory(long address) {
        if (memory.containsKey(address)) {
            memory.remove(address);
        } else {
            throw new IllegalArgumentException("Memory not allocate: " + address);
        }
    }

    /**
     * 获取指定位置的内存
     * @param address 地址
     * @return 内存地址对象
     */
    public static Map.Entry<Long, byte[]> memoryAt(long address) {
        Set<Map.Entry<Long, byte[]>> entries = memory.entrySet();
        for (Map.Entry<Long, byte[]> entry : entries) {
            Long startAddress = entry.getKey();
            byte[] mem = entry.getValue();
            long endAddress = startAddress + mem.length;
            if (startAddress <= address && address < endAddress) {
                return entry;
            }
        }
        throw new IllegalArgumentException("Invalid address: " + address);
    }

    /**
     * 在指定地址设置long值
     * @param address 地址
     * @param x long值
     */
    public static void putLong(long address, long x) {
        Map.Entry<Long, byte[]> entry = memoryAt(address);
        Long startAddress = entry.getKey();
        int offset = (int) (address - startAddress);
        ByteBuffer buf = ByteBuffer.wrap(entry.getValue());
        buf.order(ByteOrder.BIG_ENDIAN); // 大端
        buf.position(offset);
        buf.putLong(x);
    }

    /**
     * 获取指定地址的字节
     * @param address 地址
     * @return 字节
     */
    public static byte getByte(long address) {
        Map.Entry<Long, byte[]> entry = memoryAt(address);
        return entry.getValue()[0];
    }

}
