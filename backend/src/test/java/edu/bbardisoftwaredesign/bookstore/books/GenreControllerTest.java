package edu.bbardisoftwaredesign.bookstore.books;

import edu.bbardisoftwaredesign.bookstore.BaseControllerTest;
import edu.bbardisoftwaredesign.bookstore.TestCreationFactory;
import edu.bbardisoftwaredesign.bookstore.books.model.dto.BookDTO;
import edu.bbardisoftwaredesign.bookstore.books.model.dto.GenreDTO;
import edu.bbardisoftwaredesign.bookstore.books.service.GenreService;
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

public class GenreControllerTest extends BaseControllerTest {
    @InjectMocks
    private GenreController genreController;

    @Mock
    private GenreService genreService;

    @BeforeEach
    protected void setUp(){
        super.setUp();
        MockitoAnnotations.openMocks(this);
        genreController = new GenreController(genreService);
        mockMvc = MockMvcBuilders.standaloneSetup(genreController).build();
    }

    @Test
    void findAllGenres() throws Exception{
        List<GenreDTO> genreDTOS = TestCreationFactory.listOf(GenreDTO.class);
        when(genreService.findAll()).thenReturn(genreDTOS);
        ResultActions resultActions = mockMvc.perform(get(GENRES+FIND_ALL));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonContentToBe(genreDTOS));
    }

    @Test
    void createGenre() throws Exception{
        GenreDTO genreDTO = GenreDTO.builder()
                .genre(randomString())
                .build();
        when(genreService.create(genreDTO)).thenReturn(genreDTO);
        ResultActions result = performPostWithRequestBody(GENRES+CREATE,genreDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(genreDTO));
    }

    @Test
    void deleteGenre() throws Exception{
        GenreDTO genreDTO = GenreDTO.builder()
                .genre(randomString())
                .build();
        ResultActions result = performDeleteWithRequestBody(GENRES+DELETE,genreDTO);
        result.andExpect(status().isOk());
    }

    @Test
    void editGenre() throws Exception{
        GenreDTO genreDTO = GenreDTO.builder()
                .genre(randomString())
                .build();
        when(genreService.edit(genreDTO)).thenReturn(genreDTO);
        ResultActions result = performPatchWithRequestBody(GENRES+EDIT,genreDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(genreDTO));
    }

}
