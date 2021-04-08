package edu.bbardisoftwaredesign.bookstore.report;


import edu.bbardisoftwaredesign.bookstore.books.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

public class CSVReportServiceTest {
    @InjectMocks
    private CSVReportService reportService;

    @Mock
    private BookRepository bookRepository;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        reportService = new CSVReportService(bookRepository);
    }

    @Test
    void export(){
        when(bookRepository.findAll()).thenReturn(new ArrayList<>());
        ResponseEntity<?> response = reportService.export();
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(response.getBody(), "ID,Title,Author,Price\n");
    }
}
