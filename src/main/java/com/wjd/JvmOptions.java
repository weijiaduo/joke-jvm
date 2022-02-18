package com.wjd;

/**
 * @since 2022/2/15
 */
public class JvmOptions {

    private String mainClass;
    private String classpath;
    private boolean verboseClass;
    private String xJre;
    private String absJavaHome;
    private String absJreLib;

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

    public String getxJre() {
        return xJre;
    }

    public void setxJre(String xJre) {
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
}
