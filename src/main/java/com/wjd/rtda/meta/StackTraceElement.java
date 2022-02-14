package com.wjd.rtda.meta;

/**
 * @since 2022/2/14
 */
public class StackTraceElement {

    private String fileName;
    private String className;
    private String methodName;
    private int lineNumber;

    public StackTraceElement(String fileName, String className, String methodName, int lineNumber) {
        this.fileName = fileName;
        this.className = className;
        this.methodName = methodName;
        this.lineNumber = lineNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public String toString() {
        return className + "." + methodName + "(" + fileName + ":" + lineNumber + ")";
    }
}
