package libraryCatalog.repoInterfaces;

import libraryCatalog.models.Author;
import libraryCatalog.models.Book;
import libraryCatalog.models.Document;
import libraryCatalog.models.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface DocumentManagerInterface extends CrudRepository<Document,Long> {
    @Query("SELECT d FROM Document d WHERE d.name LIKE %:name%")
    Iterable<Document> getByName(@Param("name") String name);

    @Query("select max(d.id) from Document d")
    Long getMaxID();

    @Query("SELECT d FROM Document d WHERE (:name IS NULL OR d.name LIKE %:name%) " +
            "AND (:docNumber IS NULL OR d.documentNumber=:docNumber) " +
            "AND (:location IS NULL OR d.docLocation=:location) " +
            "AND (:creatDate IS NULL OR d.creationDate=:creatDate)" +
            "AND (:modDate IS NULL OR d.modificationDate=:modDate)" +
            "AND (:addDate IS NULL OR d.addedDate=:addDate)")
    Iterable<Document> parametrSearch(@Param("name") String name, @Param("docNumber") String documentNumber,
                                  @Param("location") Location docLocation, @Param("creatDate")
                                          Date creationDate, @Param("modDate") Date modDate, @Param("addDate") Date addDate);
}
