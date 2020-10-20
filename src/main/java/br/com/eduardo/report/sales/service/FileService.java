package br.com.eduardo.report.sales.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

public class FileService {

    private ArrayList<File> pendingFiles;
    private Properties applicationProperties;
    private File currentFile;

    private final String inputFileFormat = ".dat";
    private final String inputProcessedFileFormat = ".done.dat";
    private final String applicationPropertiesLocation = "static/applicationSettings.properties";

    public FileService() {
        loadProperties();
        loadDirectories();
        preLoadPendingFiles();
    }

    public boolean hasNext() {
        return !pendingFiles.isEmpty();
    }

    public File next() {
        if(!(currentFile == null)) markFileDone(currentFile);
        currentFile = pendingFiles.remove(0);
        return currentFile;
    }

    private void markFileDone(File doneProcessing) {
        String newName = doneProcessing
                .getName()
                .replace(inputFileFormat,"")
                .concat(inputProcessedFileFormat);
        doneProcessing
                .renameTo(new File(newName));
    }

    private void loadProperties() {
        applicationProperties= new Properties();
        try {
            applicationProperties
                    .load(getClass()
                            .getClassLoader()
                            .getResourceAsStream(applicationPropertiesLocation));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDirectories() {
        String base = System.getenv().get(applicationProperties.getProperty("report.base"));
        String outputDir = base + "/" + applicationProperties.getProperty("output.location");
        String inputDir = base + "/" + applicationProperties.getProperty("input.location");
        String jasper = outputDir + applicationProperties.getProperty("report.jasper");
        String print = outputDir + applicationProperties.getProperty("report.jprint");
        String separator = applicationProperties.getProperty("input.separator");

        System.setProperty("inputFileSeparator", separator);
        System.setProperty("inputDirectory", inputDir);
        System.setProperty("outputDirectory", outputDir);
        System.setProperty("tmpJasperLocation" ,jasper);
        System.setProperty("tmpPrintLocation" ,print);

        createStructure();
    }

    private void createStructure() {
        try {
            new File(System.getProperty("inputDirectory")).mkdirs();
            new File(System.getProperty("outputDirectory")).mkdirs();
            new File(System.getProperty("tmpJasperLocation")).createNewFile();
            new File(System.getProperty("tmpPrintLocation")).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void preLoadPendingFiles() {
        pendingFiles = new ArrayList<File>(Arrays.asList(
                Objects.requireNonNull(new File(System.getProperty("inputDirectory"))
                        .listFiles((d, s) -> {
                            return s.toLowerCase().endsWith(inputFileFormat)
                                    && !(s.toLowerCase().endsWith(inputProcessedFileFormat));
                        })
                )
            )
        );
    }

//    public SalesInputFile ingestInputFile() {
//        File source = new File("C:/Users/Eduardo/data/in/inputSample.dat");
//        SalesInputFile inputFile = SalesInputFile.builder().salesFile(source).separator("ç").build();
//        return inputFile;
//    }

}
