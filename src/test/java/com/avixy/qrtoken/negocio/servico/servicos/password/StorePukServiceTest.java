//package com.avixy.qrtoken.negocio.servico.servicos.password;
//
//import com.avixy.qrtoken.core.HermesModule;
//import com.avixy.qrtoken.negocio.servico.servicos.header.FFHeaderPolicy;
//import com.google.inject.Guice;
//import com.google.inject.Injector;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
///**
// * Created on 22/09/2014
// *
// * @author Breno Salgado <breno.salgado@avixy.com>
// */
//public class StorePukServiceTest {
//    StorePukService service = new StorePukService(new FFHeaderPolicy());
//
//    @Test
//    public void testServiceMsg() throws Exception {
//        byte[] expectedMsg = {
//                0b00110100, // '4'
//                0b00110100, // '4'
//                0b00110100, // '4'
//                0b00110100, // '4'
//                0b00100100, // '$'
//        };
//        service.setPuk("4444");
//    }
//}
