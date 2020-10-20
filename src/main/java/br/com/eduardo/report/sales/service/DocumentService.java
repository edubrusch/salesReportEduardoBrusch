package br.com.eduardo.report.sales.service;

import br.com.eduardo.report.sales.model.Report;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class DocumentService {

    private String templateFileLocation;
    private String outputDirectory;
    private String tmpJasperLocation;
    private String tmpPrintLocation;

    public void compileDocument(Report report) {

        loadDirectories();
        Object[] dataSource = new Object[]{report.toMap()};


        try {
            JasperCompileManager.compileReportToFile(templateFileLocation, tmpJasperLocation);
            JasperFillManager.fillReportToFile(
                    tmpJasperLocation,
                    tmpPrintLocation,
                    null,
                    new JRMapArrayDataSource(dataSource)
            );
            JasperExportManager.exportReportToPdfFile(tmpPrintLocation);

        } catch (JRException e) {
            e.printStackTrace();
        }
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



}

