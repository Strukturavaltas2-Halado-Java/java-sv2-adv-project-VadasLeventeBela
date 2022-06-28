package training.library.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import training.library.entities.Person;

public interface PersonRepository extends JpaRepository<Person,Long> {
}
