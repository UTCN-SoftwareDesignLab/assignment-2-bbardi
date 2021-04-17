package edu.bbardisoftwaredesign.bookstore.books.service;

import edu.bbardisoftwaredesign.bookstore.books.mapper.BookMapper;
import edu.bbardisoftwaredesign.bookstore.books.mapper.GenreMapper;
import edu.bbardisoftwaredesign.bookstore.books.model.Book;
import edu.bbardisoftwaredesign.bookstore.books.model.Genre;
import edu.bbardisoftwaredesign.bookstore.books.model.dto.BookDTO;
import edu.bbardisoftwaredesign.bookstore.books.repository.BookRepository;
import edu.bbardisoftwaredesign.bookstore.books.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final BookMapper bookMapper;
    private final GenreMapper genreMapper;

    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    private Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book not found: " + id));
    }

    private Genre findGenre(String genre) {
        return genreRepository.findByGenre(genre)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Genre not found: " + genre));
    }

    public void remove(Long Id) {
        bookRepository.deleteById(Id);
    }

    public boolean sell(Long id) {
        Book actBook = findById(id);
        if (actBook.getQuantity() <= 0)
            return false;
        else actBook.setQuantity(actBook.getQuantity() - 1);
        bookRepository.save(actBook);
        return true;
    }

    public BookDTO create(BookDTO book) {
        Genre actGenre = findGenre(book.getGenre());
        Book actBook = bookMapper.fromDto(book);
        actBook.setGenre(actGenre);
        return bookMapper.toDto(bookRepository.save(actBook));
    }

    public BookDTO edit(Long id, BookDTO book) {
        Genre actGenre = findGenre(book.getGenre());
        Book actBook = bookMapper.fromDto(book);
        actBook.setId(id);
        actBook.setGenre(actGenre);
        return bookMapper.toDto(bookRepository.save(actBook));
    }
}
