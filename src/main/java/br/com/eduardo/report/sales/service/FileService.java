package br.com.eduardo.report.sales.service;


import br.com.eduardo.report.sales.model.SalesInputFile;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class FileService {

    private String templateFileLocation;
    private String outputDirectory;
    private String tmpJasperLocation;
    private String tmpPrintLocation;

    public FileService() {
        loadProperties();
        loadDirectories();
    }

    public SalesInputFile ingestInputFile() {
        File source = new File("C:/Users/Eduardo/data/in/inputSample.dat");
        SalesInputFile inputFile = SalesInputFile.builder().salesFile(source).separator("ç").build();
        return inputFile;
    }


    public Properties loadProperties() {
        Properties reportSettings = new Properties();

        try {
            reportSettings
                    .load(getClass()
                            .getClassLoader()
                            .getResourceAsStream("static/jasperSettings.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return reportSettings;
    }


    private void loadDirectories() {
        templateFileLocation = getClass()
                .getClassLoader()
                .getResource("templates/template.jrxml")
                .getFile();

        Properties settings = loadProperties();
        String base = System.getenv().get(settings.getProperty("report.base"));
        String outputDir = settings.getProperty("output.location");
        String jasper = settings.getProperty("report.jasper");
        String print = settings.getProperty("report.jprint");

        outputDirectory = base + "/" +  outputDir;
        tmpJasperLocation = outputDirectory + "/" + jasper;
        tmpPrintLocation = outputDirectory + "/" + print;

        try {
            new File(outputDirectory).mkdirs();
            new File(tmpJasperLocation).createNewFile();
            new File(tmpPrintLocation).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
