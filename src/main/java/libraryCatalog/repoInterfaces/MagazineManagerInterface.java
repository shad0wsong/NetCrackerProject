package libraryCatalog.repoInterfaces;

import libraryCatalog.models.Document;
import libraryCatalog.models.Location;
import libraryCatalog.models.Magazine;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface MagazineManagerInterface extends CrudRepository<Magazine,Long> {
    @Query("SELECT m FROM Magazine m WHERE m.name LIKE %:name%")
    Iterable<Magazine> getByName(@Param("name") String name);

    @Query("select max(m.id) from Magazine m")
    Long getMaxID();

    @Query("SELECT m FROM Magazine m WHERE (:name IS NULL OR m.name LIKE %:name%) " +
            "AND (:location IS NULL OR m.magazineLocation=:location) " +
            "AND (:pubDate IS NULL OR m.publicationDate=:pubDate)" +
            "AND (:modDate IS NULL OR m.modificationDate=:modDate)" +
            "AND (:addDate IS NULL OR m.addedDate=:addDate)")
    Iterable<Magazine> parametrSearch(@Param("name") String name,
                                      @Param("location") Location location, @Param("pubDate")
                                              Date pubDate, @Param("modDate") Date modDate, @Param("addDate") Date addDate);
}
