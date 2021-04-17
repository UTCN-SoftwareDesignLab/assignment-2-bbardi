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

    @PostMapping
    public GenreDTO createGenre(@RequestBody GenreDTO genre) {
        return genreService.create(genre);
    }

    @DeleteMapping(ENTITY)
    public void deleteGenre(@PathVariable Long id) {
        genreService.delete(id);
    }

    @PatchMapping(ENTITY)
    public GenreDTO editGenre(@PathVariable Long id, @RequestBody GenreDTO genre) {
        return genreService.edit(id, genre);
    }

    @GetMapping
    public List<GenreDTO> findAllGenres() {
        return genreService.findAll();
    }

}
