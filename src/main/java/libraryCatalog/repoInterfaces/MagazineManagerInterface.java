package libraryCatalog.repoInterfaces;

import libraryCatalog.models.Magazine;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MagazineManagerInterface extends CrudRepository<Magazine,Long> {
    @Query("SELECT m FROM Magazine m WHERE m.name LIKE %:name%")
    Iterable<Magazine> getByName(@Param("name") String name);

    @Query("select max(m.id) from Magazine m")
    Long getMaxID();
}
