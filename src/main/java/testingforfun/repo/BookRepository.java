package testingforfun.repo;

import org.springframework.data.repository.CrudRepository;
import testingforfun.models.Books;

public interface BookRepository extends CrudRepository<Books,Long> {
}
