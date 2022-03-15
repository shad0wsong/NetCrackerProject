package libraryCatalog.repo;

import libraryCatalog.models.Magazines;
import libraryCatalog.models.PatentDocuments;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MagazineRepository extends CrudRepository<Magazines,Long> {
    @Query("SELECT m FROM Magazines m WHERE m.name LIKE %:name%")
    Iterable<Magazines> getByName(@Param("name") String name);
}
