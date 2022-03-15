package libraryCatalog.repo;

import libraryCatalog.models.Documents;
import libraryCatalog.models.PatentDocuments;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PatentDocumentsRepository extends CrudRepository<PatentDocuments,Long> {
    @Query("SELECT p FROM PatentDocuments p WHERE p.name LIKE %:name%")
    Iterable<PatentDocuments> getByName(@Param("name") String name);
}
