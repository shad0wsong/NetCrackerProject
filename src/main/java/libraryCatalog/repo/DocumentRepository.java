package libraryCatalog.repo;

import libraryCatalog.models.Books;
import libraryCatalog.models.Documents;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DocumentRepository extends CrudRepository<Documents,Long> {
    @Query("SELECT d FROM Documents d WHERE d.name LIKE %:name%")
    Iterable<Documents> getByName(@Param("name") String name);
}
