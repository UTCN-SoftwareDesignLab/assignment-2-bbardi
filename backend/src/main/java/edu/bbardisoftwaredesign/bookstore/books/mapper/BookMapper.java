package edu.bbardisoftwaredesign.bookstore.books.mapper;

import edu.bbardisoftwaredesign.bookstore.books.model.Book;
import edu.bbardisoftwaredesign.bookstore.books.model.dto.BookDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(source = "genre.genre", target = "genre")
    BookDTO toDto(Book book);

    @Mapping(source = "genre", target = "genre.genre")
    Book fromDto(BookDTO book);

}
