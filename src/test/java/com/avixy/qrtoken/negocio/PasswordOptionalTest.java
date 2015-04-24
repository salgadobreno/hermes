package com.avixy.qrtoken.negocio;

import com.avixy.qrtoken.negocio.qrcode.QrSetup;
import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.behaviors.PasswordOptional;
import com.avixy.qrtoken.negocio.servico.behaviors.PinAble;
import com.avixy.qrtoken.negocio.servico.operations.NoPasswordPolicy;
import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class PasswordOptionalTest {
    HeaderPolicy headerPolicy = mock(HeaderPolicy.class);
    PasswordPolicy passwordPolicy = mock(PasswordPolicy.class);

    /**
     * PasswordOptional intended implementation
     */
    class TestService extends AbstractService implements PinAble, PasswordOptional {
        final PasswordPolicy originalPasswordPolicy;
        public TestService(HeaderPolicy headerPolicy, PasswordPolicy passwordPolicy) {
            super(headerPolicy);
            this.passwordPolicy = passwordPolicy;
            this.originalPasswordPolicy = passwordPolicy;
        }
        @Override
        public void togglePasswordOptional(boolean passwordOptional) {
            if (passwordOptional) {
                this.passwordPolicy = NO_PASSWORD_POLICY;
            } else  {
                this.passwordPolicy = originalPasswordPolicy;
            }
        }

        @Override
        public void setPin(String pin) {  }
        @Override
        public String getServiceName() { return null; }
        @Override
        public ServiceCode getServiceCode() { return null; }
        @Override
        public byte[] getMessage() { return new byte[0]; }
    }
    TestService testService = new TestService(headerPolicy, passwordPolicy);

    @Before
    public void setUp() throws Exception {
        when(headerPolicy.getHeader(any())).thenReturn(new byte[0]);
        when(passwordPolicy.get()).thenReturn(new byte[0]);
    }

    @Test
    public void testPasswordOptionalDefault() throws Exception {
        testService.getQrs(mock(QrSetup.class));
        verify(passwordPolicy).get();
    }

    @Test
    public void testPasswordOptionalOff() throws Exception {
        verify(passwordPolicy, never()).get();
        testService.togglePasswordOptional(false);
        testService.getQrs(mock(QrSetup.class));
    }

    @Test
    public void testPasswordOptionalOn() throws Exception {
        testService.togglePasswordOptional(true);
        testService.togglePasswordOptional(false);
        testService.getQrs(mock(QrSetup.class));
        verify(passwordPolicy).get();
    }
}