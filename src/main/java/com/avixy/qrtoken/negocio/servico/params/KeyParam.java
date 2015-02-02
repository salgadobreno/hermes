package com.avixy.qrtoken.negocio.servico.params;

/**
 * Created on 26/09/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class KeyParam extends BinaryWrapperParam {
    // Includes CRC and key length
    private KeyLengthParam keyLength;
    private CrcParam crc;

    public KeyParam(byte[] key) {
        super(key);
        keyLength = new KeyLengthParam(key.length);
        crc = new CrcParam(key);
    }

    @Override
    public String toBinaryString() {
        return keyLength.toBinaryString() + super.toBinaryString() + crc.toBinaryString();
    }
}
