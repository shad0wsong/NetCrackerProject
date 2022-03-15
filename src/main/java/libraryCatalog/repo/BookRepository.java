package libraryCatalog.repo;

import org.springframework.data.repository.CrudRepository;
import libraryCatalog.models.Books;

public interface BookRepository extends CrudRepository<Books,Long> {
}
