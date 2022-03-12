package testingforfun.repo;

import org.springframework.data.repository.CrudRepository;
import testingforfun.models.Picture;

public interface PictureRepository extends CrudRepository<Picture,Long> {
}
