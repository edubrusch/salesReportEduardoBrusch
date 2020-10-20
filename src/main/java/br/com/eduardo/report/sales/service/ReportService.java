package br.com.eduardo.report.sales.service;

import br.com.eduardo.report.sales.model.Report;
import br.com.eduardo.report.sales.model.ReportDTO;
import br.com.eduardo.report.sales.model.SalesInputFile;

public class ReportService {

    private DocumentService documentService;
    private InputDataProcessService inputDataProcessService;
    private ReportDataProcess reportDataProcess;
    private FileService fileService;


    public void generateReport() {

        inputDataProcessService = new InputDataProcessService();
        documentService = new DocumentService();
        reportDataProcess = new ReportDataProcess();
        fileService = new FileService();

        SalesInputFile salesInputFile = fileService.ingestInputFile();
        ReportDTO dto = inputDataProcessService.processSalesInputFile(salesInputFile);
        Report report = reportDataProcess.processReport(dto);
        documentService.compileDocument(report);
    }
}
