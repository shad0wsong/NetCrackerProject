package libraryCatalog.repoInterfaces;

import libraryCatalog.models.Author;
import libraryCatalog.models.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthorManagerInterface extends CrudRepository<Author,Long> {
    @Query("SELECT a FROM Author a WHERE a.name=:name")
    Optional<Author> getByName(@Param("name") String name);

}
