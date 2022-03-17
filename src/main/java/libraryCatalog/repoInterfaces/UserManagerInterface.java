package libraryCatalog.repoInterfaces;

import org.springframework.data.repository.CrudRepository;
import libraryCatalog.models.User;

import java.util.Optional;

public interface UserManagerInterface extends CrudRepository<User,Long> {
    Optional<User> findByLogin(String login);
}
