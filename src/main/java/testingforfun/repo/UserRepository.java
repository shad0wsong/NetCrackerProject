package testingforfun.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import testingforfun.models.Users;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Users,Long> {
    Optional<Users> findByLogin(String login);
}
