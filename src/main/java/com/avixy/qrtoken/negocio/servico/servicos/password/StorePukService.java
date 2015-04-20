//package com.avixy.qrtoken.negocio.servico.servicos.password;
//
//import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
//import com.avixy.qrtoken.negocio.servico.ServiceCode;
//import com.avixy.qrtoken.negocio.servico.params.StringWithLengthParam;
//import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
//import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
//import com.google.inject.Inject;
//
///**
// * Created on 22/09/2014
// *
// * @author Breno Salgado <breno.salgado@avixy.com>
// */
//public class StorePukService extends AbstractService {
//    private StringWithLengthParam puk;
//
//    @Inject
//    public StorePukService(HeaderPolicy headerPolicy) {
//        super(headerPolicy);
//    }
//
//    @Override
//    public String getServiceName() {
//        return "Gravar PUK";
//    }
//
//    @Override
//    public ServiceCode getServiceCode() {
//        return ServiceCode.SERVICE_STORE_PUK;
//    }
//
//    @Override
//    public byte[] getMessage() {
//        return BinaryMsg.create().append(puk).toByteArray();
//    }
//
//    public void setPuk(String puk) {
//        this.puk = new StringWithLengthParam(puk);
//    }
//
//}
