package com.avixy.qrtoken.negocio.servico.chaves;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.avixy.qrtoken.negocio.servico.chaves.crypto.KeyType;
import com.sun.javafx.collections.transformation.Matcher;
import javafx.beans.binding.ListBinding;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsável pela persistência e recuperação das Chaves.
 * @author Breno Salgado <breno.salgado@avixy.com>
 *
 * Created on 21/08/2014
 */
public class ChavesSingleton {
    private static Logger logger = LoggerFactory.getLogger(ChavesSingleton.class);

    private final List<Chave> chaves = new ArrayList<>();
    private final ObservableList<Chave> observableChaves = FXCollections.observableList(chaves);
    private final File csv = new File("chaves.csv");

    private static ChavesSingleton instance = new ChavesSingleton();
    private ChavesSingleton(){}

    {
        /* le/cria arquivo de chaves */
        try {
            if (!csv.exists())
                csv.createNewFile();

            CSVReader reader = new CSVReader(new FileReader(csv));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                observableChaves.add(new Chave(nextLine[0], KeyType.valueOf(nextLine[1]), nextLine[2], Integer.valueOf(nextLine[3])));
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ChavesSingleton getInstance() { return instance; }

    public ObservableList<Chave> getObservableChaves(){ return observableChaves; }

    public ObservableList<Chave> observableChavesFor(final KeyType keyType){
        /* Cria lista filtrada */
        final Matcher<Chave> keyTypeMatcher = new Matcher<Chave>() {
            @Override
            public boolean matches(Chave chave) {
                return chave.getKeyType() == keyType;
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

    public boolean add(Chave chave){
        if (chave.isValid()) {
            observableChaves.add(chave);
            persist();
            return true;
        } else {
            return false;
        }
    }

    public void remove(Chave chave){
        observableChaves.remove(chave);
        persist();
        LoggerFactory.getLogger(ChavesSingleton.class).info("Removed {}", chave);
    }

    /** Salva o CSV */
    private void persist() {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(csv));
            for (Chave chave : chaves) {
                String[] arr = {chave.getId(), chave.getKeyType().name(), chave.getValor(), chave.getLength().toString()};
                writer.writeNext(arr);
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            logger.error("Não foi possível salvar as Chaves", e);
        }
    }
}
