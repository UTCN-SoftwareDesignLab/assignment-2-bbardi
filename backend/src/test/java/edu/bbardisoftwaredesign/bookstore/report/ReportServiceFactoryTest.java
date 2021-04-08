package edu.bbardisoftwaredesign.bookstore.report;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static edu.bbardisoftwaredesign.bookstore.report.ReportType.CSV;
import static edu.bbardisoftwaredesign.bookstore.report.ReportType.PDF;

@SpringBootTest
class ReportServiceFactoryTest {

    @Autowired
    private ReportServiceFactory reportServiceFactory;

    @Test
    void getReportService() {
        ReportService csvReportService = reportServiceFactory.getReportService(CSV);
        Assertions.assertEquals(CSV, csvReportService.getType());
        ReportService pdfReportService = reportServiceFactory.getReportService(PDF);
        Assertions.assertEquals(PDF, pdfReportService.getType());
    }
}
