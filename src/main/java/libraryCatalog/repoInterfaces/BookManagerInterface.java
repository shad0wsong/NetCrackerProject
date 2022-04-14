package libraryCatalog.repoInterfaces;

import libraryCatalog.models.Author;
import libraryCatalog.models.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import libraryCatalog.models.Book;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface BookManagerInterface extends CrudRepository<Book,Long> {
    @Query("SELECT b FROM Book b WHERE b.name LIKE %:name%")
    Iterable<Book> getByName(@Param("name") String name);

    @Query("select max(b.id) from Book b WHERE b.id<100000000")
    Long getMaxID();

    @Query("SELECT b FROM Book b WHERE (:name IS NULL OR b.name LIKE %:name%) " +
            "AND (:author IS NULL OR b.bookAuthor=:author) " +
            "AND (:location IS NULL OR b.location=:location) " +
            "AND (:isbn IS NULL OR b.ISBN=:isbn) " +
            "AND (:pubDate IS NULL OR b.publicationDate=:pubDate)" +
            "AND (:modDate IS NULL OR b.modificationDate=:modDate)" +
            "AND (:addDate IS NULL OR b.addedDate=:addDate)")
    Iterable<Book> parametrSearch(@Param("name") String name, @Param("author") Author author,
                                  @Param("location") Location location,@Param("isbn") String ISBN,@Param("pubDate")
                                          Date pubDate,@Param("modDate") Date modDate,@Param("addDate") Date addDate);
}
