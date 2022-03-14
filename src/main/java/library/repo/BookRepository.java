package library.repo;

import org.springframework.data.repository.CrudRepository;
import library.models.Books;

public interface BookRepository extends CrudRepository<Books,Long> {
}
