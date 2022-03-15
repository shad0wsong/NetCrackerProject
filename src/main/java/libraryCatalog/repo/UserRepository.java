package libraryCatalog.repo;

import org.springframework.data.repository.CrudRepository;
import libraryCatalog.models.Users;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Users,Long> {
    Optional<Users> findByLogin(String login);
}
