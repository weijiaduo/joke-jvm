package com.wjd.classfile.attr;

import com.wjd.classfile.ClassReader;
import com.wjd.classfile.type.Uint16;

/**
 * @since 2022/2/14
 */
public class ExceptionInfoTable implements AttributeInfo {

    private Uint16 exceptionLength;
    private ExceptionTableEntry[] exceptionEntries; // 异常处理表

    @Override
    public void readFrom(ClassReader reader) {
        exceptionLength = reader.readUint16();
        exceptionEntries = new ExceptionTableEntry[exceptionLength.value()];
        for (int i = 0; i < exceptionEntries.length; i++) {
            exceptionEntries[i] = readExceptionEntry(reader);
        }
    }

    private ExceptionTableEntry readExceptionEntry(ClassReader reader) {
        ExceptionTableEntry entry = new ExceptionTableEntry();
        entry.setStartPC(reader.readUint16());
        entry.setEndPC(reader.readUint16());
        entry.setHandlerPC(reader.readUint16());
        entry.setCatchPC(reader.readUint16());
        return entry;
    }

    public ExceptionTableEntry[] getExceptionEntries() {
        return exceptionEntries;
    }
}
