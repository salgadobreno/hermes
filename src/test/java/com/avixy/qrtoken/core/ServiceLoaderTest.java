package com.avixy.qrtoken.core;

import com.avixy.qrtoken.gui.servicos.components.banking.AutorizarTransferenciaBancariaServiceComponent;
import com.avixy.qrtoken.gui.servicos.components.*;
import com.avixy.qrtoken.gui.servicos.components.ktamper.EraseKtamperServiceComponent;
import com.avixy.qrtoken.gui.servicos.components.ktamper.GenerateKtamperServiceComponent;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceLoaderTest {

    @Test
    public void testGetListServicos() throws Exception {
        /* doesn't throw exception */
        ServiceLoader.getServiceComponentMap();

        List<Class<? extends ServiceComponent>> serviceComponents = new ArrayList<>();
        /* get all service components in a list */
        for (Map.Entry<ServiceCategory, List<Class<? extends ServiceComponent>>> serviceCategoryListEntry : ServiceLoader.getServiceComponentMap().entrySet()) {
            serviceComponents.addAll(serviceCategoryListEntry.getValue());
        }
        /* includes PingService */
        assertTrue(serviceComponents.contains(PingServiceComponent.class));
        /* includes HmacTemplateMessageService */
        assertTrue(serviceComponents.contains(AutorizarTransferenciaBancariaServiceComponent.class));
        /* includes GenerateKtamperServiceComponent */
        assertTrue(serviceComponents.contains(GenerateKtamperServiceComponent.class));
        /* includes EraseKtamperServiceComponent */
        assertTrue(serviceComponents.contains(EraseKtamperServiceComponent.class));



    }
}