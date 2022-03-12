package testingforfun.repo;

import org.springframework.data.repository.CrudRepository;
import testingforfun.models.Music;

public interface MusicRepository extends CrudRepository<Music,Long> {
}
