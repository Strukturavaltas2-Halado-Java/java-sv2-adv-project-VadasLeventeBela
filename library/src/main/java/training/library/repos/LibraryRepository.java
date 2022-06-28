package training.library.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import training.library.entities.Library;

public interface LibraryRepository extends JpaRepository<Library,Long> {
}
