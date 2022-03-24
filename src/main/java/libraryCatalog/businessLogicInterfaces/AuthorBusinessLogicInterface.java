package libraryCatalog.businessLogicInterfaces;

import libraryCatalog.models.Author;
import org.springframework.ui.Model;

import java.util.Optional;

public interface AuthorBusinessLogicInterface {
    void createAuthor(Author author);
    void getAllAuthor(Iterable<Author> authors, Model model);
    void getAuthorDetails(Optional<Author> author , Model model);
    void deleteAuthor(Author author);
    boolean authorExistByID(Long id);
}
