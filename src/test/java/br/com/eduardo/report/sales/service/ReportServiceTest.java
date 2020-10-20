package br.com.eduardo.report.sales.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReportServiceTest {

    @Test
    @DisplayName("Use this method to create a new report")
    public void test() {
        new ReportService().generateReport();

    }

    @Test
    @DisplayName("Actual unity test")
    public void ShouldGenerateReport() {

    }

}
