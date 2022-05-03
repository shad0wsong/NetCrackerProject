package libraryCatalog.repoInterfaces;

import libraryCatalog.models.ObjectType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ObjectTypeManagerInterface extends CrudRepository<ObjectType,Long> {

    @Query("SELECT o FROM ObjectType o WHERE o.name=:name")
    ObjectType getByName(@Param("name") String name);
}
