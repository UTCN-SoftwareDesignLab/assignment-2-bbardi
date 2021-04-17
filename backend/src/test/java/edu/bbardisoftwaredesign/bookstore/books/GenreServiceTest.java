package edu.bbardisoftwaredesign.bookstore.books;

import edu.bbardisoftwaredesign.bookstore.TestCreationFactory;
import edu.bbardisoftwaredesign.bookstore.books.mapper.GenreMapper;
import edu.bbardisoftwaredesign.bookstore.books.model.Genre;
import edu.bbardisoftwaredesign.bookstore.books.model.dto.GenreDTO;
import edu.bbardisoftwaredesign.bookstore.books.repository.GenreRepository;
import edu.bbardisoftwaredesign.bookstore.books.service.GenreService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static edu.bbardisoftwaredesign.bookstore.TestCreationFactory.randomLong;
import static edu.bbardisoftwaredesign.bookstore.TestCreationFactory.randomString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class GenreServiceTest {

    @InjectMocks
    private GenreService genreService;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private GenreMapper genreMapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        genreService = new GenreService(genreRepository,genreMapper);
    }

    @Test
    void findAll(){
        List<Genre> genres = TestCreationFactory.listOf(Genre.class);
        when(genreRepository.findAll()).thenReturn(genres);
        List<GenreDTO> genreDTOS = genreService.findAll();
        Assertions.assertEquals(genreDTOS.size(),genres.size());
    }

    @Test
    void create(){
        GenreDTO genreDTO = GenreDTO.builder()
                .genre(randomString())
                .build();
        GenreDTO genreDTOwithID = GenreDTO.builder()
                .genre(randomString())
                .id(randomLong())
                .build();
        Genre genre = Genre.builder()
                .id(genreDTOwithID.getId())
                .genre(genreDTO.getGenre())
                .build();
        when(genreMapper.fromDto(genreDTO)).thenReturn(genre);
        when(genreMapper.toDto(genre)).thenReturn(genreDTOwithID);
        when(genreRepository.save(genre)).thenReturn(genre);
        Assertions.assertEquals(genreDTOwithID.getId(),genreService.create(genreDTO).getId());
    }

    @Test
    void edit(){
        Genre genre = Genre.builder()
                .id(randomLong())
                .genre(randomString())
                .build();
        GenreDTO genreDTO = GenreDTO.builder()
                .id(genre.getId())
                .genre(genre.getGenre())
                .build();
        when(genreRepository.findById(genre.getId())).thenReturn(java.util.Optional.of(genre));
        when(genreRepository.save(genre)).thenReturn(genre);
        when(genreMapper.toDto(genre)).thenReturn(genreDTO);
        Assertions.assertEquals(genreDTO.getGenre(),genreService.edit(genreDTO.getId(),genreDTO).getGenre());
    }

    @Test
    void delete(){
        Genre genre = Genre.builder()
                .id(randomLong())
                .genre(randomString())
                .build();
        GenreDTO genreDTO = GenreDTO.builder()
                .id(genre.getId())
                .genre(genre.getGenre())
                .build();
        genreService.delete(genreDTO.getId());
    }
}
