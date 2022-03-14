package com.wjd.classpath;

import java.io.IOException;

public interface Entry {

    /**
     * 读取class文件
     * @param className class的完整名称（java/lang/Object）
     * @return 文件字节
     */
    byte[] readClass(String className) throws IOException;

    /**
     * 路径
     * @return 路径
     */
    String string();
}
