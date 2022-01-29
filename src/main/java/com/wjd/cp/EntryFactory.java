package com.wjd.cp;

import java.io.File;

public class EntryFactory {

    /**
     * 根据参数生成指定的Entry类
     *
     * @param entryOption 选项参数
     * @return 具体的Entry实例
     */
    public static Entry newEntry(String entryOption) {
        // 多选项路径
        if (entryOption.contains(File.pathSeparator)) {
            return new CompositeEntry(entryOption);
        }

        // 通配符路径
        if (entryOption.endsWith("*")) {
            return new WildcardEntry(entryOption);
        }

        // zip或者jar
        String lowName = entryOption.toLowerCase();
        if (lowName.endsWith(".zip") || lowName.endsWith(".jar")) {
            return new ZipEntry(entryOption);
        }

        // 文件夹路径
        return new DirEntry(entryOption);
    }

}
