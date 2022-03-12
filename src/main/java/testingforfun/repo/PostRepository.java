package testingforfun.repo;

import org.springframework.data.repository.CrudRepository;
import testingforfun.models.Post;

public interface PostRepository extends CrudRepository<Post,Long> {

}
