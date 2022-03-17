package libraryCatalog.businessLogic;

import libraryCatalog.JSONInterfaces.BookJSONInterface;
import libraryCatalog.businessLogicInterfaces.BookBusinessLogicInterface;
import libraryCatalog.models.Book;
import libraryCatalog.models.Document;
import libraryCatalog.repoInterfaces.BookManagerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class BookBusinessLogic implements BookBusinessLogicInterface {
    @Autowired
    BookManagerInterface bookManagerInterface;
    @Autowired
    BookJSONInterface bookJSONInterface;

    public void createBook(Book book) throws IOException {
        bookManagerInterface.save(book);
        Book copy = new Book(book.getName(),book.getISBN(),book.getAuthor(),
                book.getLocation(),book.getPublicationDate(),book.getAddedDate(),book.getModificationDate());
        Long bookID= bookManagerInterface.getMaxID();
        copy.setId(bookID);
        bookJSONInterface.BookToJSONandFile(copy);
    }
    public void getAllBooks(Iterable<Book> books,Model model){
        model.addAttribute("books",books);
    }
    public void getBookDetails(Optional<Book> book ,Model model){
        ArrayList<Book> res= new ArrayList<>();
        book.ifPresent(res::add);
        model.addAttribute("book",res);
    }
    public void editBook(Book book,Model model) throws IOException {
        bookManagerInterface.save(book);
        bookJSONInterface.BookToJSONandFile(book);
    }
    public void deleteBook(Book book){
        bookManagerInterface.delete(book);
        bookJSONInterface.DeleteJSON(book);
    }
    public boolean bookExistByID(Long id){
        if(bookManagerInterface.existsById(id)){
            return true ;
        }
        return false;
    }
    public void searchBookByName(Iterable<Book> books,Model model){
        model.addAttribute("book",books);
    }

}
