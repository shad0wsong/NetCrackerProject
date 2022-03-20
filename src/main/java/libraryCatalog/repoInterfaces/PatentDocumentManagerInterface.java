package libraryCatalog.repoInterfaces;

import libraryCatalog.models.Author;
import libraryCatalog.models.Book;
import libraryCatalog.models.Location;
import libraryCatalog.models.PatentDocument;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface PatentDocumentManagerInterface extends CrudRepository<PatentDocument,Long> {
    @Query("SELECT p FROM PatentDocument p WHERE p.name LIKE %:name%")
    Iterable<PatentDocument> getByName(@Param("name") String name);
    @Query("select max(p.id) from PatentDocument p")
    Long getMaxID();

    @Query("SELECT p FROM PatentDocument p WHERE (:name IS NULL OR p.name LIKE %:name%) " +
            "AND (:author IS NULL OR p.patentDocAuthor=:author) " +
            "AND (:location IS NULL OR p.patentDocLocation=:location) " +
            "AND (:patent IS NULL OR p.patentNumber=:patent) " +
            "AND (:modDate IS NULL OR p.modificationDate=:modDate)" +
            "AND (:addDate IS NULL OR p.addedDate=:addDate)")
    Iterable<PatentDocument> parametrSearch(@Param("name") String name, @Param("author") Author author,
                                            @Param("location") Location location, @Param("patent") String patentNumber
                                            , @Param("modDate") Date modDate, @Param("addDate") Date addDate);
}
