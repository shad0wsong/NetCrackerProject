package libraryCatalog.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import libraryCatalog.models.Book;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends CrudRepository<Book,Long> {
    @Query("SELECT b FROM Book b WHERE b.name LIKE %:name%")
    Iterable<Book> getByName(@Param("name") String name);

}
