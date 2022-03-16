package libraryCatalog.businessLogic;

import libraryCatalog.models.Book;
import libraryCatalog.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class BookBusinessLogic {
    @Autowired
    BookRepository bookRepository;

    public void createBook(String name, String ISBN, String author, String location, Date pubDate,Date addDate,Date modDate){
        Book book = new Book(name,ISBN,author,location,pubDate,addDate,modDate);
        bookRepository.save(book);
    }
    public void getAllBooks(Model model){
        Iterable<Book> books= bookRepository.findAll();
        model.addAttribute("books",books);
    }
    public void getBookDetails(Long id ,Model model){
        Optional<Book> book= bookRepository.findById(id);
        ArrayList<Book> res= new ArrayList<>();
        book.ifPresent(res::add);
        model.addAttribute("book",res);
    }
    public void editBook(Long id ,String name, String ISBN, String author, String location, Date pubDate,Date addDate,Date modDate){
        Book book = bookRepository.findById(id).orElseThrow();
        book.setName(name);
        book.setAuthor(author);
        book.setLocation(location);
        book.setISBN(ISBN);
        book.setPublicationDate(pubDate);
        book.setAddedDate(addDate);
        book.setModificationDate(modDate);
        bookRepository.save(book);
    }
    public void deleteBook(Long id){
        Book book = bookRepository.findById(id).orElseThrow();
        bookRepository.delete(book);
    }
    public boolean bookExistByID(Long id){
        if(bookRepository.existsById(id)){
            return true ;
        }
        return false;
    }
    public void searchBookByName(String name,Model model){
        Iterable<Book> books= bookRepository.getByName(name);
        model.addAttribute("book",books);
    }

}
