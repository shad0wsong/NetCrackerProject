package libraryCatalog.repo;

import org.springframework.data.repository.CrudRepository;
import libraryCatalog.models.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {
    Optional<User> findByLogin(String login);
}
