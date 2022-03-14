package com.wjd.classpath;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 类路径
 */
public class Classpath {

    /**
     * 是否打印路径
     */
    public static boolean verbosePath;

    private String cp;
    private String jrePath;

    /**
     * 查找入口
     */
    private List<Entry> entries;

    /**
     * 启动路径选项
     */
    private String jreOption;
    /**
     * 用户类路径选项
     */
    private String cpOptions;


    public Classpath(String jreOption, String cpOptions) {
        entries = new ArrayList<>();
        this.jreOption = jreOption;
        this.cpOptions = cpOptions;
        initEntries();
    }

    /**
     * 读取class文件
     *
     * @param className class的完整名称（java/lang/Object）
     * @return 文件字节
     */
    public byte[] readClass(String className) throws IOException {
        // 转换成文件路径
        String classPath = Paths.get(className).toString();
        String classFileName = classPath + ".class";

        // 查找并读取class文件
        for (Entry entry : entries) {
            byte[] bytes = entry.readClass(classFileName);
            if (bytes != null) {
                return bytes;
            }
        }
        System.out.println("not found class " + className);
        return null;
    }

    /**
     * classpath
     */
    public String string() {
        return cpOptions;
    }

    /**
     * 初始化选项
     */
    private void initEntries() {
        parseBootAndExtClasspath();
        parseUserClasspath();
    }

    public String getClassPath() {
        return cp;
    }

    /**
     * 解析启动类路径和扩展类路径
     */
    private void parseBootAndExtClasspath() {
        cp = "";
        String jrePath = getJrePath();

        // 启动类路径（jre/lib/*）
        Path bootPath = Paths.get(jrePath, "lib");
        String bootDir = bootPath.toAbsolutePath() + File.separator + "*";
        Entry bootEntry = EntryFactory.newEntry(bootDir);
        entries.add(bootEntry);
        cp += bootDir;

        // 扩展类路径（jre/lib/ext/*）
        Path extPath = Paths.get(jrePath, "lib", "ext");
        String extDir = extPath.toAbsolutePath() + File.separator + "*";
        Entry extEntry = EntryFactory.newEntry(extDir);
        entries.add(extEntry);
        cp += ";" + extDir;
    }

    /**
     * 解析用户类路径
     */
    private void parseUserClasspath() {
        if (cpOptions == null || "".equals(cpOptions)) {
            return;
        }
        // 用户类路径（-classpath/-cp）
        Entry userEntry = EntryFactory.newEntry(cpOptions);
        entries.add(userEntry);
        cp += ";" + cpOptions;
    }

    /**
     * 获取jre文件夹路径
     *
     * @return jre文件夹路径
     */
    public String getJrePath() {
        if (jrePath == null) {
            jrePath = findJrePath();
        }
        return jrePath;
    }

    /**
     * 获取jre文件夹路径
     *
     * @return jre文件夹路径
     */
    private String findJrePath() {
        Path path;

        // 用户自定义路径
        if (jreOption != null) {
            path = Paths.get(jreOption);
            if (Files.exists(path)) {
                return path.toString();
            }
        }

        // 系统环境变量
        String jdkPath = System.getenv("JAVA_HOME");
        if (jdkPath != null) {
            path = Paths.get(jdkPath, "jre");
            if (Files.exists(path)) {
                return path.toString();
            }
        }

        // 最后尝试在当前路径下寻找jre目录
        path = Paths.get(".", "jre");
        if (Files.exists(path)) {
            return path.toString();
        }

        // 找不到jre的路径
        throw new IllegalStateException("Can not found jre folder!");
    }

}
