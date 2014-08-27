package com.avixy.qrtoken.gui;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.avixy.qrtoken.negocio.servico.crypto.KeyType;
import com.sun.javafx.collections.transformation.Matcher;
import javafx.beans.binding.ListBinding;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 21/08/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
//TODO: OOP decente
public class ChavesSingleton {
    private static final List<Chave> chaves = new ArrayList<>();
    private static final ObservableList<Chave> observableChaves = FXCollections.observableList(chaves);
    private static final File csv = new File("chaves.csv");

    static {
        // le/cria arquivo de chaves
        try {
            if (!csv.exists())
                csv.createNewFile();

            // init reader
            CSVReader reader = new CSVReader(new FileReader(csv));
            // monta Lista Singleton de chaves
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                observableChaves.add(new Chave(nextLine[0], KeyType.valueOf(nextLine[1]), nextLine[2]));
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ChavesSingleton(){}

    public static ObservableList<Chave> getObservableChaves(){
        return observableChaves;
    }

    public static ObservableList<Chave> observableChaveFor(final KeyType keyType){
        // Cria lista filtrada
        final Matcher<Chave> keyTypeMatcher = new Matcher<Chave>() {
            @Override
            public boolean matches(Chave chave) {
                return chave.getAlgoritmo() == keyType;
            }
        };

        final ListBinding<Chave> filtered = new ListBinding<Chave>() {
            @Override
            protected ObservableList<Chave> computeValue() {
                ObservableList<Chave> filtered = FXCollections.observableArrayList();
                for (Chave chave : observableChaves) {
                    if (keyTypeMatcher.matches(chave))
                        filtered.add(chave);
                }
                return filtered;
            }
        };
        observableChaves.addListener(new ListChangeListener<Chave>() {
            @Override
            public void onChanged(Change<? extends Chave> change) {
                filtered.invalidate();
            }
        });

        return filtered;
    }

    public static void add(Chave chave){
        observableChaves.add(chave);
        saveCsv();
    }

    public static void remove(Chave chave){
        observableChaves.remove(chave);
        saveCsv();
        LoggerFactory.getLogger(ChavesSingleton.class).info("Removed {}", chave);
    }

    private static void saveCsv() {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(csv));
            for (Chave chave : chaves) {
                String[] arr = {chave.getId(), chave.getAlgoritmo().name(), chave.getValor()};
                writer.writeNext(arr);
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Chave {

        private String id;
        private KeyType algoritmo;
        private String valor;

        public Chave(String id, KeyType algoritmo, String valor) {
            this.id = id;
            this.algoritmo = algoritmo;
            this.valor = valor;
        }

        public String getId() {
            return id;
        }

        public KeyType getAlgoritmo() {
            return algoritmo;
        }

        public String getValor() {
            return valor;
        }

        @Override
        public String toString() {
            return getId() + " - " + getAlgoritmo();
        }
    }
}
