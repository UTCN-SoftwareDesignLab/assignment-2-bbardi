package edu.bbardisoftwaredesign.bookstore.books.service;

import edu.bbardisoftwaredesign.bookstore.books.mapper.GenreMapper;
import edu.bbardisoftwaredesign.bookstore.books.model.Genre;
import edu.bbardisoftwaredesign.bookstore.books.model.dto.GenreDTO;
import edu.bbardisoftwaredesign.bookstore.books.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public List<GenreDTO> findAll() {
        return genreRepository.findAll().stream()
                .map(genreMapper::toDto)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        genreRepository.deleteById(id);
    }

    public GenreDTO create(GenreDTO genre) {
        return genreMapper.toDto(genreRepository.save(genreMapper.fromDto(genre)));
    }

    public GenreDTO edit(Long id, GenreDTO genre) {
        Genre actGenre = genreRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Genre not found"));
        actGenre.setGenre(genre.getGenre());
        return genreMapper.toDto(genreRepository.save(actGenre));
    }
}
