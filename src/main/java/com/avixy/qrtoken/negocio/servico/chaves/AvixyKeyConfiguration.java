package com.avixy.qrtoken.negocio.servico.chaves;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.avixy.qrtoken.negocio.lib.AvixyKeyDerivator;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.CryptoException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

/**
 * Handles Avixy Key Configuration Profiles and Key Derivation
 *
 * Created on 27/04/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class AvixyKeyConfiguration {
    private final AvixyKeyDerivator avixyKeyDerivator = new AvixyKeyDerivator();

    private static final AvixyKeyConfiguration instance = new AvixyKeyConfiguration();

    private AvixyKeyConfiguration() {}

    private final File csv = new File("avxkconfig.csv");

    public static AvixyKeyConfiguration getInstance() {
        return instance;
    }

    {
        try {
            if (!csv.exists()) {
                csv.createNewFile();
            }

            CSVReader reader = new CSVReader(new FileReader(csv));
            List<String[]> keys = reader.readAll();
            avixyKeyDerivator.setKeyComponents(
                    Hex.decodeHex(keys.get(0)[0].toCharArray()),
                    Hex.decodeHex(keys.get(1)[0].toCharArray()),
                    Hex.decodeHex(keys.get(2)[0].toCharArray())
            );
            avixyKeyDerivator.setAesConstants(
                    Hex.decodeHex(keys.get(3)[0].toCharArray()),
                    Hex.decodeHex(keys.get(4)[0].toCharArray())
            );
            avixyKeyDerivator.setHmacConstants(
                    Hex.decodeHex(keys.get(5)[0].toCharArray()),
                    Hex.decodeHex(keys.get(6)[0].toCharArray())
            );
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Empty file");
        } catch (IOException | DecoderException e) {
            e.printStackTrace();
        }
    }

    public void persist() {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(csv));
            writer.writeNext(new String[] { Hex.encodeHexString(avixyKeyDerivator.getkComponent1())});
            writer.writeNext(new String[] { Hex.encodeHexString(avixyKeyDerivator.getkComponent2())});
            writer.writeNext(new String[] { Hex.encodeHexString(avixyKeyDerivator.getkComponent3())});
            writer.writeNext(new String[] { Hex.encodeHexString(avixyKeyDerivator.getkAes1())});
            writer.writeNext(new String[] { Hex.encodeHexString(avixyKeyDerivator.getkAes2())});
            writer.writeNext(new String[] { Hex.encodeHexString(avixyKeyDerivator.getkHmac1())});
            writer.writeNext(new String[] { Hex.encodeHexString(avixyKeyDerivator.getkHmac2())});

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AvixyKeyDerivator getAvixyKeyDerivator() {
        return avixyKeyDerivator;
    }

    public void setAesConstants(byte[] kAes1, byte[] kAes2) {
        avixyKeyDerivator.setAesConstants(kAes1, kAes2);
    }

    public void setHmacConstants(byte[] kHmac1, byte[] kHmac2) {
        avixyKeyDerivator.setHmacConstants(kHmac1, kHmac2);
    }

    public void setKeyComponents(byte[] keyComponent1, byte[] keyComponent2, byte[] keyComponent3) {
        avixyKeyDerivator.setKeyComponents(keyComponent1, keyComponent2, keyComponent3);
    }

    public byte[] getHmacKey(String serialNumber) throws CryptoException, GeneralSecurityException, AvixyKeyDerivator.AvixyKeyNotConfigured {
        return avixyKeyDerivator.getHmacKey(serialNumber);
    }

    public byte[] getAesKey(String serialNumber) throws CryptoException, GeneralSecurityException, AvixyKeyDerivator.AvixyKeyNotConfigured {
        return avixyKeyDerivator.getAesKey(serialNumber);
    }

}
