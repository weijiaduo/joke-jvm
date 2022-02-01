package com.wjd.rtda;

import org.junit.Test;

public class LocalVarsTest {

    @Test
    public void testLocalVars() {
        LocalVars localVars = new LocalVars(100);
        localVars.setInt(0, 100);
        localVars.setInt(1, -100);
        localVars.setLong(2, 2997924580L);
        localVars.setLong(4, -2997924580L);
        localVars.setFloat(6, 3.1415926F);
        localVars.setDouble(7, 2.71828182845);
        localVars.setRef(9, null);
        System.out.println(localVars.getInt(0));
        System.out.println(localVars.getInt(1));
        System.out.println(localVars.getLong(2));
        System.out.println(localVars.getLong(4));
        System.out.println(localVars.getFloat(6));
        System.out.println(localVars.getDouble(7));
        System.out.println(localVars.getRef(9));
    }

}