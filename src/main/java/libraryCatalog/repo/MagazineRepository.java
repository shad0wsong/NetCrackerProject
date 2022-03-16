package libraryCatalog.repo;

import libraryCatalog.models.Magazine;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MagazineRepository extends CrudRepository<Magazine,Long> {
    @Query("SELECT m FROM Magazine m WHERE m.name LIKE %:name%")
    Iterable<Magazine> getByName(@Param("name") String name);
}
