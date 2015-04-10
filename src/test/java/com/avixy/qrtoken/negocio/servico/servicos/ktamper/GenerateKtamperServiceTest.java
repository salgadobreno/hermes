package com.avixy.qrtoken.negocio.servico.servicos.ktamper;

import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.header.QrtHeaderPolicy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.TimeZone;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created on 15/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class GenerateKtamperServiceTest {
    SettableTimestampPolicy timestampPolicy = mock(SettableTimestampPolicy.class);
    GenerateKtamperService service = new GenerateKtamperService(new QrtHeaderPolicy(), timestampPolicy);

    @Before
    public void setUp() throws Exception {
        when(timestampPolicy.get()).thenReturn(new byte[0]);

        service.setTimezone(TimeZone.getTimeZone("GMT+7"));
        service.setHWVersion("1111");
        service.setPin("aaaa");
        service.setPuk("3333");
        service.setSerialNumber("1111");
    }

    @Test
    public void testMessage() throws Exception {
        String expectedMsg = "00010111" +
                "00000100" + //length
                "00110001001100010011000100110001" + //hw version
                "00000100" + //length
                "00110001001100010011000100110001" + //serialnumber
                "00000100" + //length
                "01100001011000010110000101100001" + //pin
                "00000100" + //length
                "00110011001100110011001100110011"; //puk

        assertArrayEquals(BinaryMsg.get(expectedMsg), service.getMessage());
    }

    @Test
    public void testOperations() throws Exception {
        service.getQrs(mock(QrSetup.class));
        Mockito.verify(timestampPolicy).get();
    }
}
