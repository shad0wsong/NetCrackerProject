package libraryCatalog.controllers;

import libraryCatalog.businessLogicInterfaces.BookBusinessLogicInterface;
import libraryCatalog.models.Author;
import libraryCatalog.models.Book;
import libraryCatalog.models.Location;
import libraryCatalog.repoInterfaces.AuthorManagerInterface;
import libraryCatalog.repoInterfaces.BookManagerInterface;
import libraryCatalog.repoInterfaces.LocationManagerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
public class BookController {
    static Logger log = Logger.getLogger(BookController.class.getName());
    @Autowired
    BookBusinessLogicInterface bookBusinessLogicInterface;
    @Autowired
    BookManagerInterface bookManagerInterface;
    @Autowired
    LocationManagerInterface locationManagerInterface;
    @Autowired
    AuthorManagerInterface authorManagerInterface;
    @GetMapping("/book")
    public String allBooks( Model model) {
        Iterable<Book> books= bookManagerInterface.findAll();
        bookBusinessLogicInterface.getAllBooks(books,model);
        return "book/book-main";
    }
    @GetMapping("/book/{id}")
    public String bookDetails(@PathVariable(value="id") Long id, Model model) {
        if(!bookBusinessLogicInterface.bookExistByID(id)){
            log.error("Book not found ");
            return "redirect:/error";
        }
        Optional<Book> book= bookManagerInterface.findById(id);
        bookBusinessLogicInterface.getBookDetails(book,model);
        return "book/book-details";
    }
    @PreAuthorize("hasAuthority('write')")
    @GetMapping("/book/add")
    public String addBookPage( Model model) {
        return "book/book-add";
    }

    @PreAuthorize("hasAuthority('write')")
    @PostMapping("/book/add")
    public String addBook(@RequestParam String name, @RequestParam String author, @RequestParam String location,@RequestParam String ISBN,
                          @RequestParam String publicationDate, @RequestParam String modificationDate,
                          @RequestParam String addedDate, Model model) throws ParseException, IOException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date pubDate= format.parse(publicationDate);
        Date addDate= format.parse(addedDate);
        Date modDate= format.parse(modificationDate);
        Optional<Location> locationOptional=locationManagerInterface.getByName(location);
        if(!locationOptional.isPresent()){
            log.error("Book not found ");
            return "redirect:/error";
        }
        Location locationObj=locationOptional.get();
        Optional<Author> authorOptional=authorManagerInterface.getByName(author);
        if(!authorOptional.isPresent()){
            log.error("Book not found ");
            return "redirect:/error";
        }
        Author authorObj=authorOptional.get();
        Book book = new Book(name,ISBN,authorObj, locationObj,pubDate,addDate,modDate);//CHANGE
        bookBusinessLogicInterface.createBook(book);
        log.info("Book added");
        return "book/book-done";
    }

    @PreAuthorize("hasAuthority('write')")
    @GetMapping("/book/{id}/edit")
    public String bookEditPage(@PathVariable(value="id") Long id, Model model) {
        if(!bookBusinessLogicInterface.bookExistByID(id)){
            log.error("Book not found ");
            return "redirect:/error";
        }
        Optional<Book> book= bookManagerInterface.findById(id);
        bookBusinessLogicInterface.getBookDetails(book,model);
        return "book/book-edit";
    }

    @PreAuthorize("hasAuthority('write')")
    @PostMapping("/book/{id}/edit")
    public String bookEdit(@PathVariable(value="id") Long id, @RequestParam String name, @RequestParam String author,
                           @RequestParam String location, @RequestParam String ISBN,
                           @RequestParam String publicationDate, @RequestParam String modificationDate,
                           @RequestParam String addedDate, Model model) throws ParseException, IOException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date pubDate= format.parse(publicationDate);
        Date addDate= format.parse(addedDate);
        Date modDate= format.parse(modificationDate);
        Optional<Location> locationOptional=locationManagerInterface.getByName(location);
        if(!locationOptional.isPresent()){
            log.error("Book not found ");
            return "redirect:/error";
        }
        Location locationObj=locationOptional.get();
        Optional<Author> authorOptional=authorManagerInterface.getByName(author);
        if(!authorOptional.isPresent()){
            log.error("Book not found ");
            return "redirect:/error";
        }
        Author authorObj=authorOptional.get();
        Book book = bookManagerInterface.findById(id).orElseThrow();
        book.setName(name);
        book.setAuthor(authorObj);
        book.setLocation(locationObj);
        book.setISBN(ISBN);
        book.setPublicationDate(pubDate);
        book.setAddedDate(addDate);
        book.setModificationDate(modDate);
        bookBusinessLogicInterface.editBook(book,model);
        log.info("Book updated ");
        return "redirect:/book-done";
    }
    @GetMapping("/book-done")
    public String bookDone(Model model){
        return "book/book-done";
    }

    @PreAuthorize("hasAuthority('write')")
    @PostMapping("/book/{id}/remove")
    public String bookDelete(@PathVariable(value="id") Long id, Model model) {
        Book book = bookManagerInterface.findById(id).orElseThrow();
        bookBusinessLogicInterface.deleteBook(book);
        log.info("Book deleted");
        return "redirect:/book-delete";
    }
    @GetMapping("/book-delete")
    public String bookDeleted(Model model){
        return "book/book-delete";
    }

    @GetMapping("book/parametrSearch")
    public String bookParametrSearchPage(Model model){
        return "book/book-parametrSearch";
    }
    @PostMapping("book/parametrSearch")
    public String bookParametrSearch(@RequestParam String name, @RequestParam String author,
                                    @RequestParam String location, @RequestParam String ISBN,
                                    @RequestParam String publicationDate, @RequestParam String modificationDate,
                                    @RequestParam String addedDate, Model model) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date pubDate;
        Date modDate;
        Date addDate;
        if(ISBN.equals("")) ISBN=null;

        if(!publicationDate.equals("")){
             pubDate= format.parse(publicationDate);
        }
        else pubDate =null;

        if(!modificationDate.equals("")){
             modDate= format.parse(modificationDate);
        }
        else modDate=null;
        if(!addedDate.equals("")){
             addDate= format.parse(addedDate);
        }
        else addDate=null;
        Location locationObj=locationManagerInterface.getByNameORNULL(location);
        Author authorObj=authorManagerInterface.getByNameORNULL(author);
        Iterable<Book> books = bookManagerInterface.parametrSearch(name,authorObj,locationObj,ISBN,pubDate,modDate,addDate);
        bookBusinessLogicInterface.searchBook(books,model);
        return "book/book-search";
    }

}
