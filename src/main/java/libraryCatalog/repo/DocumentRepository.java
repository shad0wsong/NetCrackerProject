package libraryCatalog.repo;

import libraryCatalog.models.Document;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DocumentRepository extends CrudRepository<Document,Long> {
    @Query("SELECT d FROM Document d WHERE d.name LIKE %:name%")
    Iterable<Document> getByName(@Param("name") String name);
}
