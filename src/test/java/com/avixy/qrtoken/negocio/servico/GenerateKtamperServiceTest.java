package com.avixy.qrtoken.negocio.servico;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created on 15/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class GenerateKtamperServiceTest {

    private GenerateKtamperService service = new GenerateKtamperService();

    @Test
    public void testServiceCode() throws Exception {
        assertEquals(20, service.getServiceCode());
    }

}
