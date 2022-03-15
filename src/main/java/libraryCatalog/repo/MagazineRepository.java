package libraryCatalog.repo;

import libraryCatalog.models.Magazines;
import org.springframework.data.repository.CrudRepository;

public interface MagazineRepository extends CrudRepository<Magazines,Long> {
}
