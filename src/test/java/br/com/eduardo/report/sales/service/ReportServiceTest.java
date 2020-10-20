package br.com.eduardo.report.sales.service;

import br.com.eduardo.report.sales.model.ReportDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReportServiceTest {

    @Mock
    private DocumentService documentService;
    @Mock
    private InputDataProcessService inputDataProcessService;
    @Mock
    private ReportDataProcess reportDataProcess;
    @Mock
    private FileService fileService;

    private ReportDTO dto;
    private File nextFile;

    @InjectMocks
    private ReportService reportService;

    @BeforeEach
    public void init() {
        dto = ReportDTO.builder().build();
        nextFile = new File("");
    }


    @Test
    @DisplayName("Use this method to create a new report")
    public void test() {
        new ReportService().generateReport();

    }

    @Test
    @DisplayName("Actual unity test")
    public void ShouldGenerateReport() {
        reportService.generateReport();
    }

}
