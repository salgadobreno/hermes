package com.avixy.qrtoken.core.extensions.binnary;

import com.avixy.qrtoken.negocio.servico.params.Param;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * BinnaryMsg encapsula a criação de uma mensagem construída atravéz de <code>Params</code>, que possuem representação binaria, em API fluida.
 * Ex.: <code>BinnaryMsg.create().append(param1).append(param2).toByArray();</code>
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 12/09/2014
 */
public class BinnaryMsg {
    private String binMsg = "";
    private static final char[] IGNORE = {'_', '.', ' '};

    private BinnaryMsg() { }

    public static BinnaryMsg create(){
        return new BinnaryMsg();
    }

    public static byte[] get(String binMsg){
        return new BinnaryMsg(binMsg).toByteArray();
    }

    public BinnaryMsg(String binMsg) {
        /* para fims de teste */
        for (char c : IGNORE) {
            binMsg = StringUtils.remove(binMsg, c);
        }
        this.binMsg = binMsg;
    }

    /**
     * @param param     Um <code>Param</code>
     * @return          A mesma instância de <code>BinnaryMsg</code> em que o append foi chamado
     */
    public BinnaryMsg append(Param param){
        binMsg += param.toBinaryString();
        return this;
    }

    /**
     * @param params    Uma lista de <code>Param</code>
     * @return          A mesma instância de <code>BinnaryMsg</code> em que o append foi chamado
     */
    public BinnaryMsg append(List<Param> params){
        for (Param param: params) {
            append(param);
        }
        return this;
    }

    public BinnaryMsg append(Param ... params){
        for (Param param : params) {
            append(param);
        }
        return this;
    }

    public byte[] toByteArray() {
//        System.out.println("binMsg = " + binMsg);
        return ExBitSet.bytesFromString(padded());
    }

    @Override
    public String toString() {
        return padded();
    }

    private String padded(){
        if (binMsg.length() % 8 != 0) {
            return StringUtils.rightPad(binMsg, (int) Math.ceil((double) binMsg.length() / 8) * 8, '0');
        } else {
            return binMsg;
        }
    }
}
