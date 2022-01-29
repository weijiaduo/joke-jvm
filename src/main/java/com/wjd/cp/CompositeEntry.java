package com.wjd.cp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 组合入口
 */
public class CompositeEntry implements Entry {

    /**
     * 路径选项
     */
    private String pathOptions;
    /**
     * 子入口列表
     */
    private List<Entry> entries;

    public CompositeEntry(String pathOptions) {
        this.pathOptions = pathOptions;
        this.initEntries();
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
            return pathOptions;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("CompositeEntry [");
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
        String[] paths = pathOptions.split(File.pathSeparator);
        entries = new ArrayList<>(paths.length);
        for (String path : paths) {
            entries.add(EntryFactory.newEntry(path));
        }
        System.out.println(string());
    }
}
