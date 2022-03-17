package libraryCatalog.repoInterfaces;

import libraryCatalog.models.Document;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DocumentManagerInterface extends CrudRepository<Document,Long> {
    @Query("SELECT d FROM Document d WHERE d.name LIKE %:name%")
    Iterable<Document> getByName(@Param("name") String name);

    @Query("select max(d.id) from Document d")
    Long getMaxID();
}
