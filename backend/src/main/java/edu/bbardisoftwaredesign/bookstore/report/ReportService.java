package edu.bbardisoftwaredesign.bookstore.report;


import org.springframework.http.ResponseEntity;

public interface ReportService {
    ResponseEntity<?> export();

    ReportType getType();
}
