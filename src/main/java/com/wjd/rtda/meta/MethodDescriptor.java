package com.wjd.rtda.meta;

/**
 * @since 2022/2/4
 */
public class MethodDescriptor {

    private String[] parameterTypes;
    private String returnType;

    public int getParamSlotCount() {
        int slotCount = 0;
        for (String type : parameterTypes) {
            slotCount++;
            if ("J".equals(type) || "D".equals(type)) {
                slotCount++;
            }
        }
        return slotCount;
    }

    public String[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(String[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
}
