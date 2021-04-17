package edu.bbardisoftwaredesign.bookstore.books;

import edu.bbardisoftwaredesign.bookstore.TestCreationFactory;
import edu.bbardisoftwaredesign.bookstore.books.model.Book;
import edu.bbardisoftwaredesign.bookstore.books.model.Genre;
import edu.bbardisoftwaredesign.bookstore.books.model.dto.BookDTO;
import edu.bbardisoftwaredesign.bookstore.books.repository.BookRepository;
import edu.bbardisoftwaredesign.bookstore.books.repository.GenreRepository;
import edu.bbardisoftwaredesign.bookstore.books.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static edu.bbardisoftwaredesign.bookstore.TestCreationFactory.*;

@SpringBootTest
public class BookServiceIntegrationTest {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;

    @BeforeEach
    void setUp(){
        bookRepository.deleteAll();
        genreRepository.deleteAll();
    }

    @Test
    void findAll(){
        List<Book> books = TestCreationFactory.listOf(Book.class);
        genreRepository.saveAll(books.stream().map(Book::getGenre).collect(Collectors.toList()));
        bookRepository.saveAll(books);
        List<BookDTO> bookDTOS = bookService.findAll();
        Assertions.assertEquals(books.size(),bookDTOS.size());
    }
    @Test
    void create(){
        BookDTO bookDTO = BookDTO.builder()
                .title(randomString())
                .author(randomString())
                .price(randomBoundedFloat(200))
                .quantity(randomLong())
                .genre(randomString())
                .description(randomString())
                .build();
        genreRepository.save(Genre.builder().genre(bookDTO.getGenre()).build());
        Assertions.assertNotNull(bookService.create(bookDTO).getId());
    }
    @Test
    void delete(){
        BookDTO bookDTO = BookDTO.builder()
                .title(randomString())
                .author(randomString())
                .price(randomBoundedFloat(200))
                .quantity(randomLong())
                .genre(randomString())
                .description(randomString())
                .build();
        genreRepository.save(Genre.builder().genre(bookDTO.getGenre()).build());
        bookDTO = bookService.create(bookDTO);
        bookService.remove(bookDTO.getId());
    }
    @Test
    void edit(){
        BookDTO bookDTO = BookDTO.builder()
                .title(randomString())
                .author(randomString())
                .price(randomBoundedFloat(200))
                .quantity(randomLong())
                .genre(randomString())
                .description(randomString())
                .build();
        genreRepository.save(Genre.builder().genre(bookDTO.getGenre()).build());
        bookDTO = bookService.create(bookDTO);
        bookDTO.setQuantity(732730L);
        Assertions.assertEquals(732730L, bookService.edit(bookDTO.getId(),bookDTO).getQuantity());
    }
    @Test
    void sell(){
        BookDTO bookDTO = BookDTO.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .price(randomBoundedFloat(200))
                .quantity(randomLong())
                .genre(randomString())
                .description(randomString())
                .build();
        genreRepository.save(Genre.builder().genre(bookDTO.getGenre()).build());
        bookDTO = bookService.create(bookDTO);
        Assertions.assertTrue(bookService.sell(bookDTO.getId()));
    }
}
