package edu.bbardisoftwaredesign.bookstore.books.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenreDTO {
    private Long id;
    private String genre;

}
