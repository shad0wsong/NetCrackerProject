package libraryCatalog.repo;

import libraryCatalog.models.Documents;
import org.springframework.data.repository.CrudRepository;

public interface DocumentRepository extends CrudRepository<Documents,Long> {
}
