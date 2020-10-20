package br.com.eduardo.report.sales.service;

import br.com.eduardo.report.sales.model.Report;
import br.com.eduardo.report.sales.model.ReportDTO;
import br.com.eduardo.report.sales.model.SalesInputFile;

import java.io.File;

public class ReportService {


    private DocumentService documentService;
    private InputDataProcessService inputDataProcessService;
    private ReportDataProcess reportDataProcess;


    public void generateReport() {

        inputDataProcessService = new InputDataProcessService();
        documentService = new DocumentService();
        reportDataProcess = new ReportDataProcess();

        File source = new File("C:/Users/Eduardo/data/in/inputSample.dat");
        SalesInputFile inputFile = SalesInputFile.builder().salesFile(source).separator("ç").build();
        ReportDTO dto = inputDataProcessService.processSalesInputFile(inputFile);
        Report report = reportDataProcess.processReport(dto);
        documentService.compileDocument(report);

    }
}
