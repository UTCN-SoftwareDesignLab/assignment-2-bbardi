package edu.bbardisoftwaredesign.bookstore.books;

import edu.bbardisoftwaredesign.bookstore.books.model.dto.GenreDTO;
import edu.bbardisoftwaredesign.bookstore.books.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static edu.bbardisoftwaredesign.bookstore.UrlMapping.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(GENRES)
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @PostMapping(CREATE)
    public GenreDTO createGenre(@RequestBody GenreDTO genre) {
        return genreService.create(genre);
    }

    @DeleteMapping(DELETE)
    public void deleteGenre(@RequestBody GenreDTO genre) {
        genreService.delete(genre);
    }

    @PatchMapping(EDIT)
    public GenreDTO editGenre(@RequestBody GenreDTO genre) {
        return genreService.edit(genre);
    }

    @GetMapping(FIND_ALL)
    public List<GenreDTO> findAllGenres() {
        return genreService.findAll();
    }

}
