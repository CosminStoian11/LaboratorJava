package ProiectLaborator.repository;

import ProiectLaborator.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findBookByTitleIgnoreCase(final String title);

    Optional<Book> findBookByAuthorIgnoreCase(final String author);
}