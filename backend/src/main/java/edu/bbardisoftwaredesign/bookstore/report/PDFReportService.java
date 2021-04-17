package edu.bbardisoftwaredesign.bookstore.report;


import edu.bbardisoftwaredesign.bookstore.books.model.Book;
import edu.bbardisoftwaredesign.bookstore.books.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class PDFReportService implements ReportService {

    private final BookRepository bookRepository;
    private static final String header = "ID;Title;Author;Price";
    private static final Integer ENTRIES_PER_PAGE = 22;

    private String parseBook(Book book) {
        return String.format("%d;%s;%s;%.2f", book.getId(), book.getTitle(), book.getAuthor(), book.getPrice());
    }

    private PDPageContentStream initializeStream(PDDocument document, PDPage page) throws IOException {
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 15);
        contentStream.newLineAtOffset(25, 725);
        contentStream.setLeading(14.5f);
        contentStream.newLine();
        contentStream.showText(header);
        contentStream.newLine();
        contentStream.newLine();
        return contentStream;
    }

    private ByteArrayOutputStream generatePDF() throws IOException {
        List<Book> books = bookRepository.findAll().stream().filter(book -> book.getQuantity() == 0).collect(Collectors.toList());

        PDDocument document = new PDDocument();

        PDPage page = new PDPage();
        PDPageContentStream contentStream = initializeStream(document, page);
        int count = 0;
        for (Book book : books) {
            contentStream.showText(parseBook(book));
            contentStream.newLine();
            contentStream.newLine();
            count++;
            if (count % ENTRIES_PER_PAGE == 0) {//reinitialize the stream to go to the next page
                contentStream.endText();
                contentStream.close();
                document.addPage(page);
                page = new PDPage();
                contentStream = initializeStream(document, page);
            }
        }
        contentStream.endText();
        contentStream.close();
        document.addPage(page);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        document.save(stream);
        return (stream);
    }

    @Override
    public ResponseEntity<?> export() {
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(generatePDF().toByteArray());
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(stream));
        } catch (IOException e) {
            return ResponseEntity.badRequest()
                    .body("Unable to generate PDF");
        }
    }

    @Override
    public ReportType getType() {
        return ReportType.PDF;
    }
}
