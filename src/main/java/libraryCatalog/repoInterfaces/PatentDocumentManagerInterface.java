package libraryCatalog.repoInterfaces;

import libraryCatalog.models.PatentDocument;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PatentDocumentManagerInterface extends CrudRepository<PatentDocument,Long> {
    @Query("SELECT p FROM PatentDocument p WHERE p.name LIKE %:name%")
    Iterable<PatentDocument> getByName(@Param("name") String name);
    @Query("select max(p.id) from PatentDocument p")
    Long getMaxID();
}
