package com.wjd.cp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 通配符入口
 */
public class WildcardEntry implements Entry {

    /**
     * 绝对路径
     */
    private String absPath;
    /**
     * 子入口列表
     */
    private List<Entry> entries;

    public WildcardEntry(String path) {
        // 去掉后面的通配符
        String p = path.substring(0, path.length() - 1);
        this.absPath = Paths.get(p).toAbsolutePath().toString();
        initEntries();
    }

    @Override
    public byte[] readClass(String className) throws IOException {
        if (entries == null) {
            return null;
        }
        for (Entry e : entries) {
            byte[] bytes = e.readClass(className);
            if (bytes != null) {
                return bytes;
            }
        }
        return null;
    }

    @Override
    public String string() {
        if (entries == null) {
            return absPath;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("WildcardEntry [");
        for (Entry e : entries) {
            sb.append(e.string()).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("]");
        return sb.toString();
    }

    /**
     * 初始化Entries列表
     */
    private void initEntries() {
        File dir = new File(absPath);
        if (!dir.isDirectory()) {
            return;
        }

        // 获取目录下所有的zip或jar文件
        File[] files = dir.listFiles((dir1, name) -> {
            String lowerName = name.toLowerCase();
            return lowerName.endsWith(".zip") || lowerName.endsWith(".jar");
        });
        if (files == null) {
            return;
        }

        // 生成所有子入口实例
        entries = new ArrayList<>(files.length);
        for (File file : files) {
            String filePath = file.getAbsolutePath();
            entries.add(EntryFactory.newEntry(filePath));
        }
        System.out.println(string());
    }
}
