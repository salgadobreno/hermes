package com.avixy.qrtoken.gui;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.avixy.qrtoken.negocio.servico.crypto.KeyPolicy;
import com.sun.javafx.collections.transformation.FilterableList;
import com.sun.javafx.collections.transformation.FilteredList;
import com.sun.javafx.collections.transformation.Matcher;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 21/08/2014
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class ChavesSingleton {
    private static final List<Chave> chaves = new ArrayList<>();
    private static final ObservableList<Chave> observableChaves = FXCollections.observableList(chaves);
    private static CSVReader reader;
    private static CSVWriter writer;
    private static final File csv = new File("chaves.csv");

    static {
        // le/cria arquivo de chaves
        try {
            if (!csv.exists())
                csv.createNewFile();

            // init reader
            reader = new CSVReader(new FileReader(csv));
            // monta List
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                observableChaves.add(new Chave(nextLine[0], nextLine[1], nextLine[2]));
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ChavesSingleton(){}

    public static List<Chave> getChaves(){
        return chaves;
    }

    public static ObservableList<Chave> getObservableChaves(){
        return observableChaves;
    }

    public static ObservableList<Chave> observableChaveFor(final KeyPolicy.KeyType keyType){
        final FilteredList<Chave> filteredList = new FilteredList<Chave>(chaves, new Matcher<Chave>() {
            @Override
            public boolean matches(Chave chave) {
                return KeyPolicy.KeyType.valueOf(chave.getAlgoritmo()) == keyType;
            }
        }, FilterableList.FilterMode.BATCH);
        filteredList.filter();
        observableChaves.addListener(new ListChangeListener<Chave>() {
            @Override
            public void onChanged(Change<? extends Chave> change) {
                filteredList.filter();
            }
        });

        return filteredList;
    }

    public static void addChave(Chave chave){
        observableChaves.add(chave);
        saveCsv();
    }

    private static void saveCsv() {
        // init writer
        try {
            writer = new CSVWriter(new FileWriter(csv));
            for (Chave chave : chaves) {
                String[] arr = {chave.getId(), chave.getAlgoritmo(), chave.getValor()};
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
        private String algoritmo;
        private String valor;

        public Chave(String id, String algoritmo, String valor) {
            this.id = id;
            this.algoritmo = algoritmo;
            this.valor = valor;
        }

        public String getId() {
            return id;
        }

        public void setId(String nome) {
            this.id = nome;
        }

        public String getAlgoritmo() {
            return algoritmo;
        }

        public void setAlgoritmo(String idade) {
            this.algoritmo = idade;
        }

        public String getValor() {
            return valor;
        }

        public void setValor(String email) {
            this.valor = email;
        }

    }
}
