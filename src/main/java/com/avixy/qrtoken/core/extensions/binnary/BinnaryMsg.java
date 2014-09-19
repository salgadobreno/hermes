package com.avixy.qrtoken.core.extensions.binnary;

import com.avixy.qrtoken.negocio.servico.params.Param;

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

    private BinnaryMsg() { }

    public static BinnaryMsg create(){
        return new BinnaryMsg();
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

    public byte[] toByteArray() {
        //TODO: block-length/validation/padding ?
        return ExBitSet.bytesFromString(binMsg);
    }
}
