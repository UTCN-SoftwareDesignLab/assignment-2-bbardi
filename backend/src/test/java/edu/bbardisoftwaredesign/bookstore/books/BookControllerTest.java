package edu.bbardisoftwaredesign.bookstore.books;

import edu.bbardisoftwaredesign.bookstore.BaseControllerTest;
import edu.bbardisoftwaredesign.bookstore.TestCreationFactory;
import edu.bbardisoftwaredesign.bookstore.books.model.dto.BookDTO;
import edu.bbardisoftwaredesign.bookstore.books.service.BookService;
import edu.bbardisoftwaredesign.bookstore.report.ReportServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static edu.bbardisoftwaredesign.bookstore.TestCreationFactory.*;
import static edu.bbardisoftwaredesign.bookstore.UrlMapping.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookControllerTest extends BaseControllerTest {
    @InjectMocks
    private BookController booksController;

    @Mock
    private BookService bookService;

    @Mock
    private ReportServiceFactory reportServiceFactory;


    @BeforeEach
    protected void setUp(){
        super.setUp();
        MockitoAnnotations.openMocks(this);
        booksController = new BookController(bookService,reportServiceFactory);
        mockMvc = MockMvcBuilders.standaloneSetup(booksController).build();
    }

    @Test
    void findAllBooks() throws Exception{
        List<BookDTO> bookDTOS = TestCreationFactory.listOf(BookDTO.class);
        when(bookService.findAll()).thenReturn(bookDTOS);
        ResultActions resultActions = mockMvc.perform(get(BOOKS));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonContentToBe(bookDTOS));
    }

    @Test
    void createBook() throws Exception{
        BookDTO book = BookDTO.builder()
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(randomBoundedFloat(200))
                .quantity(randomLong())
                .build();
        when(bookService.create(book)).thenReturn(book);
        ResultActions result = performPostWithRequestBody(BOOKS,book);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(book));
    }

    @Test
    void deleteBook() throws Exception{
        BookDTO book = BookDTO.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(randomBoundedFloat(200))
                .quantity(randomLong())
                .build();
        ResultActions result = performDeleteWithPathVariable(BOOKS+ENTITY,book.getId().toString());
        result.andExpect(status().isOk());
    }

    @Test
    void editBook() throws Exception{
        BookDTO book = BookDTO.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(randomBoundedFloat(200))
                .quantity(randomLong())
                .build();
        when(bookService.edit(book.getId(),book)).thenReturn(book);
        ResultActions result = performPatchWithRequestBodyAndPathVariable(BOOKS+ENTITY,book.getId().toString(),book);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(book));
    }

    @Test
    void sellBook() throws Exception{
        BookDTO book = BookDTO.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(randomBoundedFloat(200))
                .quantity(randomLong())
                .build();
        when(bookService.sell(book.getId())).thenReturn(true);
        ResultActions result = performPostWithPathVariable(BOOKS+ENTITY+SELL_BOOK,book.getId().toString());
        result.andExpect(status().isOk());
        when(bookService.sell(book.getId())).thenReturn(false);
        result = performPostWithPathVariable(BOOKS+ENTITY+SELL_BOOK,book.getId().toString());
        result.andExpect(status().isBadRequest());
    }

}
