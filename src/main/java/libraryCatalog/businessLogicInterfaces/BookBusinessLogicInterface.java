package libraryCatalog.businessLogicInterfaces;

import libraryCatalog.models.Book;
import libraryCatalog.models.Document;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.Optional;

public interface BookBusinessLogicInterface {
    void createBook(Book book) throws IOException;
    void getAllBooks(Iterable<Book> books, Model model);
    void getBookDetails(Optional<Book> book, Model model);
    void editBook(Book book,Model model) throws IOException;
    void deleteBook(Book book);
    boolean bookExistByID(Long id);
    void searchBook(Iterable<Book> books,Model model);
}
