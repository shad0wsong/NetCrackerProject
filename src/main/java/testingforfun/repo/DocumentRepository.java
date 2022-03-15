package testingforfun.repo;

import testingforfun.models.Documents;
import org.springframework.data.repository.CrudRepository;

public interface DocumentRepository extends CrudRepository<Documents,Long> {
}
