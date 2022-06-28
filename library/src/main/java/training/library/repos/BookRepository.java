package training.library.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import training.library.entities.Book;

public interface BookRepository extends JpaRepository<Book,Long> {
}
