package com.avixy.qrtoken.negocio.servico.chaves;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.avixy.qrtoken.negocio.lib.KeyDerivator;
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
public class ClientKeyConfiguration {
    private final KeyDerivator keyDerivator = new KeyDerivator();
    private static SimpleObjectProperty<ClientKeyConfiguration> selectedProfileProperty = new SimpleObjectProperty<>();

    private static final ObservableList<ClientKeyConfiguration> configList = FXCollections.observableArrayList(); //TODO:rename
    private String name;

    private static final File csv = new File("clientkconfig.csv");

    public class ClientKeyNotConfigured extends Exception {}

    private BooleanProperty selectedProperty = new SimpleBooleanProperty(false);

    public ClientKeyConfiguration() {
        selectedProfileProperty.addListener((observable1, oldValue1, newValue1) -> {
            newValue1.selectedProperty.setValue(true);
        });
        selectedProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                for (ClientKeyConfiguration clientKeyConfiguration : configList) {
                    if (clientKeyConfiguration != this) clientKeyConfiguration.selectedProperty().set(false);
                }
            }
        });
    }

    public BooleanProperty selectedProperty() { return selectedProperty; }

    public static ClientKeyConfiguration getSelected() {
        if (selectedProfileProperty.get() == null) {
            return new ClientKeyConfiguration();
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
                ClientKeyConfiguration config = new ClientKeyConfiguration();
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
            selectedProfileProperty.addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    select(newValue);
                }
            });
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Empty file");
        } catch (IOException | DecoderException e) {
            e.printStackTrace();
        }
    }

    public static void persist() {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(csv));
            for (ClientKeyConfiguration config : configList) {
                String[] csvContent = new String[] {
                        config.getName(),
                        Hex.encodeHexString(config.getKeyDerivator().getkComponent1()),
                        Hex.encodeHexString(config.getKeyDerivator().getkComponent2()),
                        Hex.encodeHexString(config.getKeyDerivator().getkComponent3()),
                        Hex.encodeHexString(config.getKeyDerivator().getkAes1()),
                        Hex.encodeHexString(config.getKeyDerivator().getkAes2()),
                        Hex.encodeHexString(config.getKeyDerivator().getkHmac1()),
                        Hex.encodeHexString(config.getKeyDerivator().getkHmac2()),
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

    public static void remove(ClientKeyConfiguration avixyKeyConfiguration) {
        configList.remove(avixyKeyConfiguration);
        persist();
    }

    public KeyDerivator getKeyDerivator() {
        return keyDerivator;
    }

    public void setAesConstants(byte[] kAes1, byte[] kAes2) {
        keyDerivator.setAesConstants(kAes1, kAes2);
    }

    public void setHmacConstants(byte[] kHmac1, byte[] kHmac2) {
        keyDerivator.setHmacConstants(kHmac1, kHmac2);
    }

    public void setKeyComponents(byte[] keyComponent1, byte[] keyComponent2, byte[] keyComponent3) {
        keyDerivator.setKeyComponents(keyComponent1, keyComponent2, keyComponent3);
    }

    public byte[] getHmacKey(String serialNumber) throws CryptoException, GeneralSecurityException, ClientKeyNotConfigured {
        if (serialNumber.length() < 10) {
            throw new IllegalArgumentException("O número de série deve conter 10 digitos");
        }
        try {
            return keyDerivator.getHmacKey(serialNumber);
        } catch (KeyDerivator.KeyNotConfigured keyNotConfigured) {
            throw new ClientKeyNotConfigured();
        }
    }

    public byte[] getAesKey(String serialNumber) throws CryptoException, GeneralSecurityException, ClientKeyNotConfigured {
        if (serialNumber.length() < 10) {
            throw new IllegalArgumentException("O número de série deve conter 10 digitos");
        }
        try {
            return keyDerivator.getAesKey(serialNumber);
        } catch (KeyDerivator.KeyNotConfigured keyNotConfigured) {
            throw new ClientKeyNotConfigured();
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ObservableList<ClientKeyConfiguration> getConfigList() {
        return configList;
    }

    @Override
    public String toString() {
        return name;
    }

    public static void select(ClientKeyConfiguration newValue) {
        selectedProfileProperty.set(newValue);
        persist();
    }
}
