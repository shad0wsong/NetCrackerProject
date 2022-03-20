package libraryCatalog.repoInterfaces;

import libraryCatalog.models.Document;
import libraryCatalog.models.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LocationManagerInterface extends CrudRepository<Location,Long> {
    @Query("SELECT l FROM Location l WHERE l.name=:name")
    Optional<Location> getByName(@Param("name") String name);

    @Query("SELECT l FROM Location l WHERE l.name=:name")
    Location getByNameORNULL(@Param("name") String name);
}
