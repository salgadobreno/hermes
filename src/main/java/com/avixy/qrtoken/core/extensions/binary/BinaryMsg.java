package com.avixy.qrtoken.core.extensions.binary;

import com.avixy.qrtoken.negocio.servico.params.Param;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Encapsulates the creation of a <code>byte</code> array from a binary <code>String</code> message or {@link com.avixy.qrtoken.negocio.servico.params.Param}.
 * Ex.: <code>BinnaryMsg.create().append(param1).append(param2).toByArray()</code>
 * Ex2.: <code>BinnaryMsg.get("0001").toByteArray()</code>
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 12/09/2014
 */
public class BinaryMsg {
    private String binMsg = "";
    private static final char[] IGNORE = {'_', '.', ' '};

    private BinaryMsg() { }

    public static BinaryMsg create(){
        return new BinaryMsg();
    }

    public static byte[] get(String binMsg){
        return new BinaryMsg(binMsg).toByteArray();
    }

    public BinaryMsg(String binMsg) {
        /* para fims de teste */
        for (char c : IGNORE) {
            binMsg = StringUtils.remove(binMsg, c);
        }
        this.binMsg = binMsg;
    }

    /**
     * @param param
     * @return The same <code>BinnaryMsg</code> instance
     */
    public BinaryMsg append(Param param){
        binMsg += param.toBinaryString();
        return this;
    }

    /**
     * @param params
     * @return The same <code>BinnaryMsg</code> instance
     */
    public BinaryMsg append(List<Param> params){
        for (Param param: params) {
            append(param);
        }
        return this;
    }

    /**
     * @param params
     * @return The same <code>BinnaryMsg</code> instance
     */
    public BinaryMsg append(Param ... params){
        for (Param param : params) {
            append(param);
        }
        return this;
    }

    /**
     * @return Binary message converted into a <code>byte</code> array
     */
    public byte[] toByteArray() {
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
