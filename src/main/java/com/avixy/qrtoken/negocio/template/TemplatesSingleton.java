package com.avixy.qrtoken.negocio.template;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 26/02/2015
 *
 * @author I7
 */
public class TemplatesSingleton {

    private final List<Template> templates = new ArrayList<>();
    private final ObservableList<Template> observableTemplates = FXCollections.observableList(templates);
    private final File csv = new File("templates.csv");

    private static TemplatesSingleton instance = new TemplatesSingleton();
    private TemplatesSingleton(){}

    {
        /* le/cria arquivo */
        try {
            if (!csv.exists()) {
                csv.createNewFile();
            }

            CSVReader reader = new CSVReader(new FileReader(csv));
            String[] nextLine;
            int count = 0;
            while ((nextLine = reader.readNext()) != null) {
                try {
                    observableTemplates.add(Template.fromBinary(nextLine[0], nextLine[1].trim()));
                } catch (ArrayIndexOutOfBoundsException e) {
                    observableTemplates.addAll(new Template());
                }
                count++;
            }
            reader.close();

            for (int i = 0; i < 10 - count; i++) {
                observableTemplates.add(new Template());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TemplatesSingleton getInstance() { return instance; }

    public ObservableList<Template> getObservableTemplates() { return observableTemplates; }

//    public boolean add(Template template){
//        observableTemplates.add(template);
//        persist();
//        return true;
//    }

    public void remove(Template chave){
        observableTemplates.remove(chave);
        persist();
    }

    /** Salva o CSV */
    private void persist() {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(csv));
            for (Template template : templates) {
                String[] arr = {template.getName(), template.toBinary()};
                writer.writeNext(arr);
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void persist(Template template) {
        try {
            int index = 1 + templates.indexOf(template);
//            System.out.println("index = " + index);

            BufferedReader reader = new BufferedReader(new FileReader("templates.csv"));
            StringWriter writer = new StringWriter();
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            String line;
            int i = 1;
            for (int j = 0; j < 10; j++) {
                line = reader.readLine();
                if (line == null) {
                    if (i == index) {
                        bufferedWriter.write(template.toCSV());
                    } else {
                        bufferedWriter.newLine();
                    }
                } else {
                    if (i == index) {
                        bufferedWriter.write(template.toCSV());
                    } else {
                        bufferedWriter.write(line);
                        bufferedWriter.newLine();
                    }
                }
                i++;
            }
            reader.close();

            bufferedWriter.flush();
            new FileOutputStream("templates.csv").write(writer.toString().getBytes());
            bufferedWriter.close();

            template.setDirty(false);
            template.storeState();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
