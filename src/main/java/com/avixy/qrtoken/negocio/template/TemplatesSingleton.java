package com.avixy.qrtoken.negocio.template;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.avixy.qrtoken.core.extensions.binnary.BinnaryMsg;
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

            for (int i = 0; i < Token.TEMPLATE_QTY - count; i++) {
                observableTemplates.add(new Template());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TemplatesSingleton getInstance() { return instance; }

    public ObservableList<Template> getObservableTemplates() { return observableTemplates; }

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

    enum TEMPLATE_SIZE {
        SHORT(10, 200), LONG(15, 400);
        private final int indexUpTo;
        private final int size;

        TEMPLATE_SIZE(int indexUpTo, int size) {
            this.indexUpTo = indexUpTo;
            this.size = size;
        }

        public int getSize() {
            return size;
        }

        public int getIndexUpTo() {
            return indexUpTo;
        }
    }
    public void persist(Template template) throws TemplateOverflowException {
        try {
            int index = templates.indexOf(template); //TODO: acho que esse index não é mais relevante

            BufferedReader reader = new BufferedReader(new FileReader("templates.csv"));
            StringWriter writer = new StringWriter();
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            String line;
            for (int i = 0; i < Token.TEMPLATE_QTY; i++) {
                line = reader.readLine();
                if (line == null) {
                    if (i == index) {
//                        bufferedWriter.write(template.toCSV());
                        storeTemplate(template, i < TEMPLATE_SIZE.SHORT.getIndexUpTo() ? TEMPLATE_SIZE.SHORT : TEMPLATE_SIZE.LONG, bufferedWriter);
                    } else {
                        bufferedWriter.newLine();
                    }
                } else {
                    if (i == index) {
//                        bufferedWriter.write(template.toCSV());
                        storeTemplate(template, i < TEMPLATE_SIZE.SHORT.getIndexUpTo() ? TEMPLATE_SIZE.SHORT : TEMPLATE_SIZE.LONG, bufferedWriter);
                    } else {
                        bufferedWriter.write(line);
                        bufferedWriter.newLine();
                    }
                }
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

    public class TemplateOverflowException extends Exception {
        public TemplateOverflowException(String message) {
            super(message);
        }
    }

    private void storeTemplate(Template template, TEMPLATE_SIZE templateSize, BufferedWriter bufferedWriter) throws IOException, TemplateOverflowException {
        if (BinnaryMsg.get(template.toBinary()).length < templateSize.getSize() ) {
            bufferedWriter.write(template.toCSV());
        } else {
            throw new TemplateOverflowException("Template " + templateSize.name() + " max size is " + templateSize.getSize() + "."
            + System.lineSeparator() + "Template size was " + template.toBinary().length());
        }
    }
}
