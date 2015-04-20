//package com.avixy.qrtoken.negocio.servico.servicos.chaves;
//
//import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
//import com.avixy.qrtoken.negocio.servico.ServiceCode;
//import com.avixy.qrtoken.negocio.servico.behaviors.PinAble;
//import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
//import com.avixy.qrtoken.negocio.servico.operations.PasswordPolicy;
//import com.avixy.qrtoken.negocio.servico.operations.SettableTimestampPolicy;
//import com.avixy.qrtoken.negocio.servico.behaviors.TimestampAble;
//import com.avixy.qrtoken.negocio.servico.params.KeyLengthParam;
//import com.avixy.qrtoken.negocio.servico.params.KeyTypeParam;
//import com.avixy.qrtoken.negocio.servico.servicos.AbstractService;
//import com.avixy.qrtoken.negocio.servico.servicos.header.HeaderPolicy;
//import com.google.inject.Inject;
//
//import java.util.Date;
//
///**
// * Created on 23/09/2014
// *
// * @author Breno Salgado <breno.salgado@avixy.com>
// */
//public class CreateAndExportSymKeyService extends AbstractService implements PinAble, TimestampAble {
//
//    private KeyTypeParam keyType;
//    private KeyLengthParam keyLength;
//
//    @Inject
//    public CreateAndExportSymKeyService(HeaderPolicy headerPolicy, SettableTimestampPolicy timestampPolicy, PasswordPolicy passwordPolicy) {
//        super(headerPolicy);
//        this.timestampPolicy = timestampPolicy;
//        this.passwordPolicy = passwordPolicy;
//    }
//
//    @Override
//    public String getServiceName() {
//        return "SERVICE_CREATE_AND_EXPORT_SYM_KEY";
//    }
//
//    @Override
//    public ServiceCode getServiceCode() {
//        return ServiceCode.SERVICE_CREATE_AND_EXPORT_SYM_KEY;
//    }
//
//    @Override
//    public byte[] getMessage() {
//        return BinaryMsg.create().append(keyType, keyLength).toByteArray();
//    }
//
//    public void setPin(String pin) {
//        this.passwordPolicy.setPassword(pin);
//    }
//
//    public void setKeyType(KeyType keyType){
//        this.keyType = new KeyTypeParam(keyType);
//    }
//
//    public void setKeyLength(int keyLength){
//        this.keyLength = new KeyLengthParam(keyLength);
//    }
//
//    @Override
//    public void setTimestamp(Date date){
//        this.timestampPolicy.setDate(date);
//    }
//}
