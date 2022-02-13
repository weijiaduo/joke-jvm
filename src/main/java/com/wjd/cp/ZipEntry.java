package com.wjd.cp;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.nio.file.Paths;

/**
 * zip或jar入口
 */
public class ZipEntry implements Entry {

    /**
     * 文件绝对路径
     */
    private String absPath;

    public ZipEntry(String path) {
        this.absPath = Paths.get(path).toAbsolutePath().toString();
    }

    @Override
    public byte[] readClass(String className) throws IOException {
        try (FileInputStream fis = new FileInputStream(absPath);
             BufferedInputStream buf = new BufferedInputStream(fis);
             ArchiveInputStream in = new ArchiveStreamFactory().createArchiveInputStream(buf)) {
            ArchiveEntry entry;
            while ((entry = in.getNextEntry()) != null) {
                if (!in.canReadEntryData(entry)) {
                    continue;
                }
                // 转成路径格式一样的才能对比得上完整名称
                String entryPath = Paths.get(entry.getName()).toString();
                if (entryPath.equals(className)) {
                    if (Classpath.verbosePath) {
                        System.out.println(className + " found in " + string());
                    }
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    IOUtils.copy(in, out);
                    return out.toByteArray();
                }
            }
        } catch (ArchiveException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String string() {
        return absPath;
    }
}
