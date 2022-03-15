package libraryCatalog.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import libraryCatalog.models.Books;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends CrudRepository<Books,Long> {
    @Query("SELECT b FROM Books b WHERE b.name LIKE %:name%")
    Iterable<Books> getByName(@Param("name") String name);

}
