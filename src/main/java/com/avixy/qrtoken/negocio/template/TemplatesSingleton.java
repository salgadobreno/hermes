package com.avixy.qrtoken.negocio.template;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.avixy.qrtoken.core.extensions.binary.BinaryMsg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton that manages the {@link com.avixy.qrtoken.negocio.template.Template} database
 *
 * Created on 26/02/2015
 *
 * @author Breno Salgado <breno.salgado@avixy.com>
 */
public class TemplatesSingleton {

    /**
     * Thrown when {@link com.avixy.qrtoken.negocio.template.Template} size exceeds {@link com.avixy.qrtoken.negocio.template.TemplatesSingleton.templateSize}
     */
    public class TemplateOverflowException extends Exception {
        public TemplateOverflowException(String message) {
            super(message);
        }
    }


    enum templateSize {
        SHORT(10, 200), LONG(15, 400);
        private final int indexUpTo;
        private final int size;

        templateSize(int indexUpTo, int size) {
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

    private final List<Template> templates = new ArrayList<>();
    /** Reference to the database templates */
    private final ObservableList<Template> observableTemplates = FXCollections.observableList(templates);
    private final File csv = new File("templates.csv");

    private static TemplatesSingleton instance = new TemplatesSingleton();
    private TemplatesSingleton(){}

    /* Reads or creates CSV file */
    {
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

    /**
     * Updates the CSV file with the current state of <code>ObservableTemplates</code>
     * */
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

    /**
     * Updates a single line the CSV file with the template
     *
     * @param template
     * @throws TemplateOverflowException if <code>Template</code> size exceeds {@link com.avixy.qrtoken.negocio.template.TemplatesSingleton.templateSize} limit
     */
    public void persist(Template template) throws TemplateOverflowException {
        try {
            int index = templates.indexOf(template);

            BufferedReader reader = new BufferedReader(new FileReader("templates.csv"));
            StringWriter writer = new StringWriter();
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            String line;
            for (int i = 0; i < Token.TEMPLATE_QTY; i++) {
                line = reader.readLine();
                if (line == null) {
                    if (i == index) {
                        storeTemplate(template, i < templateSize.SHORT.getIndexUpTo() ? templateSize.SHORT : templateSize.LONG, bufferedWriter);
                    } else {
                        bufferedWriter.newLine();
                    }
                } else {
                    if (i == index) {
                        storeTemplate(template, i < templateSize.SHORT.getIndexUpTo() ? templateSize.SHORT : templateSize.LONG, bufferedWriter);
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

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void storeTemplate(Template template, templateSize templateSize, BufferedWriter bufferedWriter) throws IOException, TemplateOverflowException {
        if (BinaryMsg.get(template.toBinary()).length < templateSize.getSize() ) {
            bufferedWriter.write(template.toCSV());
        } else {
            throw new TemplateOverflowException("Template " + templateSize.name() + " max size is " + templateSize.getSize() + "."
            + System.lineSeparator() + "Template size was " + BinaryMsg.get(template.toBinary()).length);
        }
    }
}
