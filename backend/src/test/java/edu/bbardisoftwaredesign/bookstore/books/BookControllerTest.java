package edu.bbardisoftwaredesign.bookstore.books;

import edu.bbardisoftwaredesign.bookstore.BaseControllerTest;
import edu.bbardisoftwaredesign.bookstore.TestCreationFactory;
import edu.bbardisoftwaredesign.bookstore.books.model.dto.BookDTO;
import edu.bbardisoftwaredesign.bookstore.books.service.BookService;
import edu.bbardisoftwaredesign.bookstore.report.ReportService;
import edu.bbardisoftwaredesign.bookstore.report.ReportServiceFactory;
import edu.bbardisoftwaredesign.bookstore.report.ReportType;
import edu.bbardisoftwaredesign.bookstore.security.dto.MessageResponse;
import edu.bbardisoftwaredesign.bookstore.user.UsersController;
import edu.bbardisoftwaredesign.bookstore.user.model.dto.UserDTO;
import edu.bbardisoftwaredesign.bookstore.user.service.UserManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
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
    private BooksController booksController;

    @Mock
    private BookService bookService;

    @Mock
    private ReportServiceFactory reportServiceFactory;


    @BeforeEach
    protected void setUp(){
        super.setUp();
        MockitoAnnotations.openMocks(this);
        booksController = new BooksController(bookService,reportServiceFactory);
        mockMvc = MockMvcBuilders.standaloneSetup(booksController).build();
    }

    @Test
    void findAllBooks() throws Exception{
        List<BookDTO> bookDTOS = TestCreationFactory.listOf(BookDTO.class);
        when(bookService.findAll()).thenReturn(bookDTOS);
        ResultActions resultActions = mockMvc.perform(get(BOOKS+FIND_ALL));
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
        ResultActions result = performPostWithRequestBody(BOOKS+CREATE,book);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(book));
    }

    @Test
    void deleteBook() throws Exception{
        BookDTO book = BookDTO.builder()
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(randomBoundedFloat(200))
                .quantity(randomLong())
                .build();
        ResultActions result = performDeleteWithRequestBody(BOOKS+DELETE,book);
        result.andExpect(status().isOk());
    }

    @Test
    void editBook() throws Exception{
        BookDTO book = BookDTO.builder()
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(randomBoundedFloat(200))
                .quantity(randomLong())
                .build();
        when(bookService.edit(book)).thenReturn(book);
        ResultActions result = performPatchWithRequestBody(BOOKS+EDIT,book);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(book));
    }

    @Test
    void sellBook() throws Exception{
        BookDTO book = BookDTO.builder()
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(randomBoundedFloat(200))
                .quantity(randomLong())
                .build();
        when(bookService.sell(book)).thenReturn(true);
        ResultActions result = performPostWithRequestBody(BOOKS+SELL_BOOK,book);
        result.andExpect(status().isOk());
        when(bookService.sell(book)).thenReturn(false);
        result = performPostWithRequestBody(BOOKS+SELL_BOOK,book);
        result.andExpect(status().isBadRequest());
    }

}
