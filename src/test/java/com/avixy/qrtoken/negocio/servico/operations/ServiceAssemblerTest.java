package com.avixy.qrtoken.negocio.servico.operations;

import com.avixy.qrtoken.negocio.Token;
import com.avixy.qrtoken.negocio.servico.ServiceAssembler;
import com.avixy.qrtoken.negocio.servico.ServiceCode;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.HmacKeyPolicy;
import com.avixy.qrtoken.negocio.servico.servicos.*;
import com.avixy.qrtoken.negocio.servico.operations.header.HeaderPolicy;
import com.avixy.qrtoken.negocio.servico.operations.header.QrtHeaderPolicy;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class ServiceAssemblerTest {
    ServiceAssembler serviceAssembler = new ServiceAssembler();

    byte[] expectedOut;
    byte[] pinBytes;

    HeaderPolicy headerPolicy = new QrtHeaderPolicy();
    SettableTimestampPolicy timestampPolicy = new SettableTimestampPolicy();
    HmacKeyPolicy hmacKeyPolicy = new HmacKeyPolicy();
    PasswordPolicy passwordPolicy = new PasswordPolicy();

    TimestampPolicy noTimestampPolicy = new NoTimestampPolicy();
    MessagePolicy noMessagePolicy = new DefaultMessagePolicy();
    HmacKeyPolicy noMacPolicy = new NoMacPolicy();
    PasswordPolicy noPasswordPolicy = new NoPasswordPolicy();

    Service service = new AbstractService(headerPolicy) {
        @Override
        public String getServiceName() {
            return "teste";
        }

        @Override
        public ServiceCode getServiceCode() {
            return ServiceCode.SERVICE_EMPTY;
        }

        @Override
        public byte[] getMessage() {
            return "abcd1234".getBytes();
        }
    };

    @Before
    public void setUp() throws Exception {
        timestampPolicy.setDate(new Date(0));
        passwordPolicy.setPassword("1234");
        pinBytes = new byte[]{'1','2','3','4',0,0,0,0,0,0,0,0,0,0,0,0};
        hmacKeyPolicy.setKey("senha123".getBytes());
    }

    @Test
    public void testGetComHeader() throws Exception {
        expectedOut = new byte[]{0, 0, Token.PROTOCOL_VERSION,0, 'a', 'b', 'c', 'd', '1', '2', '3', '4', 'x', 'x', 'x', 'x', 'x', 'x'}; // monkey patch :/
        assertArrayEquals(expectedOut, serviceAssembler.get(service, headerPolicy, noTimestampPolicy, noMessagePolicy, noMacPolicy, noPasswordPolicy));
    }

    @Test
    public void testComHeaderETimestamp() throws Exception {
        expectedOut = new byte[]{0,0,Token.PROTOCOL_VERSION,0,0,0,0,0,'a','b','c','d','1','2','3','4', 'x', 'x'}; // monkey patch :/
        assertArrayEquals(expectedOut, serviceAssembler.get(service, headerPolicy, timestampPolicy, noMessagePolicy, noMacPolicy, noPasswordPolicy));
    }

    @Test
    public void testComHeaderTimestampEPin() throws Exception {
        expectedOut = new byte[]{0,0,Token.PROTOCOL_VERSION,0,0,0,0,0,'a','b','c','d','1','2','3','4','1','2','3','4',0,0,0,0,0,0,0,0,0,0,0,0}; // monkey patch :/
        assertArrayEquals(expectedOut, serviceAssembler.get(service, headerPolicy, timestampPolicy, noMessagePolicy, noMacPolicy, passwordPolicy));
    }

    @Test
    public void testComHmacEPin() throws Exception {
        expectedOut = new byte[]{0,0,Token.PROTOCOL_VERSION,0,'a','b','c','d','1','2','3','4'};
        byte[] outComHmac = hmacKeyPolicy.apply(expectedOut);
        byte[] outComHmacEPin = ArrayUtils.addAll(outComHmac, pinBytes);
        assertArrayEquals(outComHmacEPin, serviceAssembler.get(service, headerPolicy, noTimestampPolicy, noMessagePolicy, hmacKeyPolicy, passwordPolicy));
    }
}