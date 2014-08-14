package com.avixy.qrtoken.core;

import org.junit.Test;

public class ServiceLoaderTest {

    @Test
    public void testGetListServicos() throws Exception {
        //Should not throw exception
        ServiceLoader.getListServicos();
    }
}