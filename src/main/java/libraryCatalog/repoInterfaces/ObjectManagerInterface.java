package libraryCatalog.repoInterfaces;


import libraryCatalog.models.Object;
import org.springframework.data.repository.CrudRepository;

public interface ObjectManagerInterface extends CrudRepository<Object,Long> {
}
