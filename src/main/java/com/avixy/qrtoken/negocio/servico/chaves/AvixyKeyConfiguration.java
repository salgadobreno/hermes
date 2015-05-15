package com.avixy.qrtoken.negocio.servico.chaves;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.avixy.qrtoken.negocio.lib.AvixyKeyDerivator;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.CryptoException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Handles Avixy Key Configuration Profiles and Key Derivation
 *
 * Created on 27/04/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class AvixyKeyConfiguration {
    private final AvixyKeyDerivator avixyKeyDerivator = new AvixyKeyDerivator();
    private static SimpleObjectProperty<AvixyKeyConfiguration> selectedProfileProperty = new SimpleObjectProperty<>();

    private static final ObservableList<AvixyKeyConfiguration> configList = FXCollections.observableArrayList(); //TODO:rename
    private String name;

    private static final File csv = new File("avxkconfig.csv");

    private BooleanProperty selectedProperty = new SimpleBooleanProperty(false);
    {
        selectedProfileProperty.addListener((observable, oldValue, newValue) -> {
            selectedProperty.set(newValue == this);
            persist();
        });
        selectedProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                for (AvixyKeyConfiguration avixyKeyConfiguration : configList) {
                    if (avixyKeyConfiguration != this) avixyKeyConfiguration.selectedProperty().set(false);
                }
            }
        });
    }

    public BooleanProperty selectedProperty() { return selectedProperty; }

    public static AvixyKeyConfiguration getSelected() {
        if (selectedProfileProperty.get() == null) {
            return new AvixyKeyConfiguration();
        } else {
            return selectedProfileProperty.get();
        }
    }

    static {
        try {
            if (!csv.exists()) {
                csv.createNewFile();
            }
            CSVReader reader = new CSVReader(new FileReader(csv));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                AvixyKeyConfiguration config = new AvixyKeyConfiguration();
                config.setName(nextLine[0]);
                config.setKeyComponents(
                        Hex.decodeHex(nextLine[1].toCharArray()),
                        Hex.decodeHex(nextLine[2].toCharArray()),
                        Hex.decodeHex(nextLine[3].toCharArray())
                );
                config.setAesConstants(
                        Hex.decodeHex(nextLine[4].toCharArray()),
                        Hex.decodeHex(nextLine[5].toCharArray())
                );
                config.setHmacConstants(
                        Hex.decodeHex(nextLine[6].toCharArray()),
                        Hex.decodeHex(nextLine[7].toCharArray())
                );
                if (nextLine[8].equals("true") && selectedProfileProperty.get() == null) { selectedProfileProperty.set(config); }

                configList.add(config);
            }
            if (configList.size() > 0 && getSelected() == null) { selectedProfileProperty.set(configList.get(0)); }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Empty file");
        } catch (IOException | DecoderException e) {
            e.printStackTrace();
        }
    }

    public static void persist() {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(csv));
            for (AvixyKeyConfiguration config : configList) {
                String[] csvContent = new String[] {
                        config.getName(),
                        Hex.encodeHexString(config.getAvixyKeyDerivator().getkComponent1()),
                        Hex.encodeHexString(config.getAvixyKeyDerivator().getkComponent2()),
                        Hex.encodeHexString(config.getAvixyKeyDerivator().getkComponent3()),
                        Hex.encodeHexString(config.getAvixyKeyDerivator().getkAes1()),
                        Hex.encodeHexString(config.getAvixyKeyDerivator().getkAes2()),
                        Hex.encodeHexString(config.getAvixyKeyDerivator().getkHmac1()),
                        Hex.encodeHexString(config.getAvixyKeyDerivator().getkHmac2()),
                        config == getSelected() ? "true" : "false"
                };
                writer.writeNext(csvContent);
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void remove(AvixyKeyConfiguration avixyKeyConfiguration) {
        configList.remove(avixyKeyConfiguration);
        persist();
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
        if (serialNumber.length() < 10) {
            throw new IllegalArgumentException("SerialNumber should be 10 characters long.");
        }
        return avixyKeyDerivator.getHmacKey(serialNumber);
    }

    public byte[] getAesKey(String serialNumber) throws CryptoException, GeneralSecurityException, AvixyKeyDerivator.AvixyKeyNotConfigured {
        if (serialNumber.length() < 10) {
            throw new IllegalArgumentException("SerialNumber should be 10 characters long.");
        }
        return avixyKeyDerivator.getAesKey(serialNumber);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ObservableList<AvixyKeyConfiguration> getConfigList() {
        return configList;
    }

    @Override
    public String toString() {
        return name;
    }

    public static void select(AvixyKeyConfiguration newValue) {
        selectedProfileProperty.set(newValue);
        persist();
    }
}
