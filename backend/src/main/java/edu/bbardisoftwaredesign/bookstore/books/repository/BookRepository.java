package edu.bbardisoftwaredesign.bookstore.books.repository;

import edu.bbardisoftwaredesign.bookstore.books.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
