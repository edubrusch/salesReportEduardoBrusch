package br.com.eduardo.report.sales.service;

import br.com.eduardo.report.sales.model.Report;
import br.com.eduardo.report.sales.model.ReportDTO;

import java.util.ArrayList;
import java.util.List;

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

        List<ReportDTO> reportContent = new ArrayList<ReportDTO>();

        while(fileService.hasNext()) {
                reportContent.add(inputDataProcessService.processSalesInputFile(fileService.next()));
        }
        List<Report> report = reportDataProcess.processReport(reportContent);
        documentService.compileDocument(report);
    }

}
