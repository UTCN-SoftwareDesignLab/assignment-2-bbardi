package edu.bbardisoftwaredesign.bookstore.report;

import edu.bbardisoftwaredesign.bookstore.books.model.Book;
import edu.bbardisoftwaredesign.bookstore.books.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CSVReportService implements ReportService {
    private final String csvHeader = "ID,Title,Author,Price\n";
    private final BookRepository bookRepository;

    private String generateCSV() {
        List<Book> books = bookRepository.findAll().stream().filter(book -> book.getQuantity() == 0).collect(Collectors.toList());
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append(csvHeader);
        for (Book book : books) {
            reportBuilder.append(book.getId());
            reportBuilder.append(",");
            reportBuilder.append(book.getTitle());
            reportBuilder.append(",");
            reportBuilder.append(book.getAuthor());
            reportBuilder.append(",");
            reportBuilder.append(book.getPrice());
            reportBuilder.append("\n");
        }
        return reportBuilder.toString();
    }

    @Override
    public ResponseEntity<?> export() {
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(generateCSV());
    }

    @Override
    public ReportType getType() {
        return ReportType.CSV;
    }
}
