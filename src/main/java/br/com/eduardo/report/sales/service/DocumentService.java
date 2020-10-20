package br.com.eduardo.report.sales.service;

import br.com.eduardo.report.sales.model.Report;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;

import java.util.ArrayList;
import java.util.List;

public class DocumentService {

    private final String templateResourceLocation = "templates/template.jrxml";

    public void compileDocument(List<Report> report) {

        String tmpPrintLocation = System.getProperty("tmpPrintLocation");
        String tmpJasperLocation = System.getProperty("tmpJasperLocation");

        List<Object> result = new ArrayList<Object>();
        report.forEach(r -> result.add(r.toMap()));

        try {

            JRMapArrayDataSource debug = new JRMapArrayDataSource(result.toArray());
            debug.getData();

            JasperCompileManager.compileReportToFile(getReportTemplateLocation(), tmpJasperLocation);
            JasperFillManager.fillReportToFile(
                    tmpJasperLocation,
                    tmpPrintLocation,
                    null,
                    new JRMapArrayDataSource(result.toArray())
            );
            JasperExportManager.exportReportToPdfFile(tmpPrintLocation);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    private String getReportTemplateLocation() {
        return getClass()
                .getClassLoader()
                .getResource(templateResourceLocation)
                .getFile();
    }
}


