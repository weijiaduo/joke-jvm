package com.wjd;

/**
 * @since 2022/2/15
 */
public class JvmOptions {

    /** 最大栈深度 */
    private static final int defaultMaxStackSize = 1024;

    private String mainClass;
    private String classpath;
    private boolean verboseClass;
    private String xJre;
    private String absJavaHome;
    private String absJreLib;
    private int maxStackSize;

    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public String getClasspath() {
        return classpath;
    }

    public void setClasspath(String classpath) {
        this.classpath = classpath;
    }

    public boolean isVerboseClass() {
        return verboseClass;
    }

    public void setVerboseClass(boolean verboseClass) {
        this.verboseClass = verboseClass;
    }

    public String getXJre() {
        return xJre;
    }

    public void setXJre(String xJre) {
        this.xJre = xJre;
    }

    public String getAbsJavaHome() {
        return absJavaHome;
    }

    public void setAbsJavaHome(String absJavaHome) {
        this.absJavaHome = absJavaHome;
    }

    public String getAbsJreLib() {
        return absJreLib;
    }

    public void setAbsJreLib(String absJreLib) {
        this.absJreLib = absJreLib;
    }

    public int getMaxStackSize() {
        if (maxStackSize <= 0) {
            return defaultMaxStackSize;
        }
        return maxStackSize;
    }

    public void setMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
    }
}
