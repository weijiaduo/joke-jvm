package com.wjd.cp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 目录入口
 */
public class DirEntry implements Entry {

    /**
     * 目录绝对路径
     */
    private String absPath;

    public DirEntry(String path) {
        this.absPath = Paths.get(path).toAbsolutePath().toString();
    }

    @Override
    public byte[] readClass(String className) throws IOException {
        Path path = Paths.get(absPath, className);
        if (Files.exists(path)) {
            System.out.println(className + " found in " + string());
            return Files.readAllBytes(path);
        }
        return null;
    }

    @Override
    public String string() {
        return absPath;
    }
}
