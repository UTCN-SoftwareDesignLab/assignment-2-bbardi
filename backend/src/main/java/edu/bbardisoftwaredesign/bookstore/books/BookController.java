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
public class BookController {
    private final BookService bookService;
    private final ReportServiceFactory reportServiceFactory;

    @GetMapping
    public List<BookDTO> findAllBooks() {
        return bookService.findAll();
    }

    @PostMapping
    public BookDTO createBook(@RequestBody BookDTO book) {
        return bookService.create(book);
    }

    @DeleteMapping(ENTITY)
    public void deleteBook(@PathVariable Long id) {
        bookService.remove(id);
    }

    @PostMapping(ENTITY + SELL_BOOK)
    public ResponseEntity<?> sellBook(@PathVariable Long id) {
        if (bookService.sell(id)) {
            return ResponseEntity.ok(new MessageResponse("Successfully sold book"));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Book not in stock"));
        }
    }

    @PatchMapping(ENTITY)
    public BookDTO editBook(@PathVariable Long id,@RequestBody BookDTO book) {
        return bookService.edit(id, book);
    }

    @GetMapping(EXPORT_REPORT)
    public ResponseEntity<?> getReport(@PathVariable ReportType type) {
        return reportServiceFactory.getReportService(type).export();
    }
}
