package edu.bbardisoftwaredesign.bookstore.books;

import edu.bbardisoftwaredesign.bookstore.TestCreationFactory;
import edu.bbardisoftwaredesign.bookstore.books.model.Genre;
import edu.bbardisoftwaredesign.bookstore.books.model.dto.GenreDTO;
import edu.bbardisoftwaredesign.bookstore.books.repository.BookRepository;
import edu.bbardisoftwaredesign.bookstore.books.repository.GenreRepository;
import edu.bbardisoftwaredesign.bookstore.books.service.GenreService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static edu.bbardisoftwaredesign.bookstore.TestCreationFactory.randomLong;
import static edu.bbardisoftwaredesign.bookstore.TestCreationFactory.randomString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GenreServiceIntegrationTest {

    @Autowired
    private GenreService genreService;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp(){
        bookRepository.deleteAll();
        genreRepository.deleteAll();
    }

    @Test
    void findAll(){
        List<Genre> genres = TestCreationFactory.listOf(Genre.class);
        genreRepository.saveAll(genres);
        List<GenreDTO> genreDTOS = genreService.findAll();
        Assertions.assertEquals(genreDTOS.size(),genres.size());
    }

    @Test
    void create(){
        GenreDTO genreDTO = GenreDTO.builder()
                .genre(randomString())
                .build();
        Assertions.assertNotNull(genreService.create(genreDTO).getId());
    }

    @Test
    void edit(){
        GenreDTO genreDTO = GenreDTO.builder().genre(randomString()).build();
        genreDTO = genreService.create(genreDTO);
        genreDTO.setGenre(randomString());
        Assertions.assertEquals(genreDTO.getGenre(),genreService.edit(genreDTO.getId(),genreDTO).getGenre());
    }

    @Test
    void delete(){
        GenreDTO genreDTO = GenreDTO.builder().genre(randomString()).build();
        genreDTO = genreService.create(genreDTO);
        genreService.delete(genreDTO.getId());
        Assertions.assertEquals(genreService.findAll().size(), 0);
    }
}
