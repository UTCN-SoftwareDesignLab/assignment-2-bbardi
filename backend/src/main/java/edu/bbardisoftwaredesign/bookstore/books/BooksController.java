package edu.bbardisoftwaredesign.bookstore.books;

import edu.bbardisoftwaredesign.bookstore.books.model.dto.BookDTO;
import edu.bbardisoftwaredesign.bookstore.books.service.BookService;
import edu.bbardisoftwaredesign.bookstore.report.ReportServiceFactory;
import edu.bbardisoftwaredesign.bookstore.report.ReportType;
import edu.bbardisoftwaredesign.bookstore.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static edu.bbardisoftwaredesign.bookstore.UrlMapping.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class BooksController {
    private final BookService bookService;
    private final ReportServiceFactory reportServiceFactory;

    @GetMapping(FIND_ALL)
    public List<BookDTO> findAllBooks() {
        return bookService.findAll();
    }

    @DeleteMapping(DELETE)
    public void deleteBook(@RequestBody BookDTO book) {
        bookService.remove(book);
    }

    @PostMapping(SELL_BOOK)
    public ResponseEntity<?> sellBook(@RequestBody BookDTO book) {
        if (bookService.sell(book)) {
            return ResponseEntity.ok(new MessageResponse("Successfully sold book"));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Book not in stock"));
        }
    }

    @PostMapping(CREATE)
    public BookDTO createBook(@RequestBody BookDTO book) {
        return bookService.create(book);
    }

    @PatchMapping(EDIT)
    public BookDTO editBook(@RequestBody BookDTO book) {
        return bookService.edit(book);
    }

    @GetMapping(EXPORT_REPORT)
    public ResponseEntity<?> getReport(@PathVariable ReportType type) {
        return reportServiceFactory.getReportService(type).export();
    }
}
