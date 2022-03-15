package libraryCatalog.repo;

import libraryCatalog.models.PatentDocuments;
import org.springframework.data.repository.CrudRepository;

public interface PatentDocumentsRepository extends CrudRepository<PatentDocuments,Long> {
}
