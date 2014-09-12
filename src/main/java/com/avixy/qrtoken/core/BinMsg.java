package com.avixy.qrtoken.core;

import com.avixy.qrtoken.negocio.servico.params.Param;

import java.util.List;

/**
 * Created on 12/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class BinMsg {
    private String binMsg = "";

    private BinMsg() { }

    public static BinMsg getInstance(){
        return new BinMsg();
    }

    public BinMsg append(Param param){
        if (param.toBinaryString().length() % 8 != 0)
            throw new IllegalArgumentException("Not valid bytes!!");
        binMsg += param.toBinaryString();
        return this;
    }

    public BinMsg append(List<Param> params){
        for (Param param: params) {
            append(param);
        }
        return this;
    }

    public byte[] toByteArray() {
        return ExBitSet.bytesFromString(binMsg);
    }
}
