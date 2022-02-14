package com.wjd.rtda.meta;

import com.wjd.rtda.meta.cons.ClassRef;

/**
 * 异常处理
 * @since 2022/2/14
 */
public class ExceptionHandler {

    private int startPC;
    private int endPC;
    private int handlerPC;
    private ClassRef catchType;

    public ExceptionHandler(int startPC, int endPC, int handlerPC, ClassRef catchType) {
        this.startPC = startPC;
        this.endPC = endPC;
        this.handlerPC = handlerPC;
        this.catchType = catchType;
    }

    public int getStartPC() {
        return startPC;
    }

    public int getEndPC() {
        return endPC;
    }

    public int getHandlerPC() {
        return handlerPC;
    }

    public ClassRef getCatchType() {
        return catchType;
    }
}
