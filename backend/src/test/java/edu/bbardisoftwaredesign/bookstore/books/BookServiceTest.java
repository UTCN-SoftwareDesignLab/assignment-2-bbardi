package edu.bbardisoftwaredesign.bookstore.books;

import edu.bbardisoftwaredesign.bookstore.TestCreationFactory;
import edu.bbardisoftwaredesign.bookstore.books.mapper.BookMapper;
import edu.bbardisoftwaredesign.bookstore.books.mapper.GenreMapper;
import edu.bbardisoftwaredesign.bookstore.books.model.Book;
import edu.bbardisoftwaredesign.bookstore.books.model.Genre;
import edu.bbardisoftwaredesign.bookstore.books.model.dto.BookDTO;
import edu.bbardisoftwaredesign.bookstore.books.model.dto.GenreDTO;
import edu.bbardisoftwaredesign.bookstore.books.repository.BookRepository;
import edu.bbardisoftwaredesign.bookstore.books.repository.GenreRepository;
import edu.bbardisoftwaredesign.bookstore.books.service.BookService;
import edu.bbardisoftwaredesign.bookstore.books.service.GenreService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static edu.bbardisoftwaredesign.bookstore.TestCreationFactory.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BookServiceTest {
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private GenreMapper genreMapper;

    @Mock
    private BookMapper bookMapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository,genreRepository,bookMapper,genreMapper);
    }

    @Test
    void findAll(){
        List<Book> books = TestCreationFactory.listOf(Book.class);
        when(bookRepository.findAll()).thenReturn(books);
        when(genreRepository.findByGenre(any())).thenReturn(Optional.of(Genre.builder().genre(randomString()).build()));
        List<BookDTO> bookDTOS = bookService.findAll();
        Assertions.assertEquals(bookDTOS.size(),books.size());
    }

    @Test
    void create(){
        BookDTO bookDTO = BookDTO.builder()
                .title(randomString())
                .quantity(randomLong())
                .author(randomString())
                .price(randomBoundedFloat(200))
                .genre(randomString())
                .build();
        BookDTO bookDTOwithID = BookDTO.builder()
                .title(randomString())
                .quantity(randomLong())
                .author(randomString())
                .price(randomBoundedFloat(200))
                .genre(randomString())
                .id(randomLong())
                .build();
        Book book = Book.builder()
                .title(randomString())
                .quantity(randomLong())
                .author(randomString())
                .price(randomBoundedFloat(200))
                .id(randomLong())
                .build();
        when(bookMapper.fromDto(bookDTO)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDTOwithID);
        when(bookRepository.save(book)).thenReturn(book);
        when(genreRepository.findByGenre(any())).thenReturn(Optional.of(Genre.builder().genre(randomString()).build()));
        Assertions.assertEquals(bookDTOwithID.getId(), bookService.create(bookDTO).getId());
    }

    @Test
    void edit(){
        Book book = Book.builder()
                .title(randomString())
                .quantity(randomLong())
                .author(randomString())
                .price(randomBoundedFloat(200))
                .id(randomLong())
                .build();
        BookDTO bookDTO = BookDTO.builder()
                .title(book.getTitle())
                .quantity(book.getQuantity())
                .author(book.getAuthor())
                .price(book.getPrice())
                .id(book.getId())
                .build();
        when(bookMapper.fromDto(bookDTO)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDTO);
        when(bookRepository.save(book)).thenReturn(book);
        when(genreRepository.findByGenre(any())).thenReturn(Optional.of(Genre.builder().genre(randomString()).build()));
        Assertions.assertEquals(bookDTO.getId(), bookService.edit(bookDTO).getId());
    }

    @Test
    void delete(){
        Book book = Book.builder()
                .title(randomString())
                .quantity(randomLong())
                .author(randomString())
                .price(randomBoundedFloat(200))
                .id(randomLong())
                .build();
        BookDTO bookDTO = BookDTO.builder()
                .title(book.getTitle())
                .quantity(book.getQuantity())
                .author(book.getAuthor())
                .price(book.getPrice())
                .id(book.getId())
                .build();
        when(bookMapper.toDto(book)).thenReturn(bookDTO);
        when(bookMapper.fromDto(bookDTO)).thenReturn(book);
        bookService.remove(bookDTO);
    }
    @Test
    void sell(){
        Book book = Book.builder()
                .title(randomString())
                .quantity(randomLong())
                .author(randomString())
                .price(randomBoundedFloat(200))
                .id(randomLong())
                .build();
        BookDTO bookDTO = BookDTO.builder()
                .title(book.getTitle())
                .quantity(book.getQuantity())
                .author(book.getAuthor())
                .price(book.getPrice())
                .id(book.getId())
                .build();
        when(bookMapper.fromDto(bookDTO)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDTO);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(genreRepository.findByGenre(any())).thenReturn(Optional.of(Genre.builder().genre(randomString()).build()));
        Assertions.assertEquals(bookDTO.getQuantity()>0 ,bookService.sell(bookDTO));
    }
}
