package edu.bbardisoftwaredesign.bookstore.books.service;

import edu.bbardisoftwaredesign.bookstore.books.mapper.GenreMapper;
import edu.bbardisoftwaredesign.bookstore.books.model.dto.GenreDTO;
import edu.bbardisoftwaredesign.bookstore.books.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public void delete(GenreDTO genre) {
        genreRepository.delete(genreMapper.fromDto(genre));
    }

    public GenreDTO create(GenreDTO genre) {
        return genreMapper.toDto(genreRepository.save(genreMapper.fromDto(genre)));
    }

    public GenreDTO edit(GenreDTO genre) {
        return genreMapper.toDto(genreRepository.save(genreMapper.fromDto(genre)));
    }
}
