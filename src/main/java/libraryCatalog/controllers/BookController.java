package libraryCatalog.controllers;

import libraryCatalog.businessLogic.BookBusinessLogic;
import libraryCatalog.businessLogicInterfaces.BookBusinessLogicInterface;
import libraryCatalog.models.Book;
import libraryCatalog.repoInterfaces.BookManagerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
public class BookController {
    @Autowired
    BookBusinessLogicInterface bookBusinessLogicInterface;
    @Autowired
    BookManagerInterface bookManagerInterface;
    @GetMapping("/book")
    public String allBooks( Model model) {
        Iterable<Book> books= bookManagerInterface.findAll();
        bookBusinessLogicInterface.getAllBooks(books,model);
        return "book/book-main";
    }
    @GetMapping("/book/{id}")
    public String bookDetails(@PathVariable(value="id") Long id, Model model) {
        if(!bookBusinessLogicInterface.bookExistByID(id)){
            return "redirect:/error";
        }
        Optional<Book> book= bookManagerInterface.findById(id);
        bookBusinessLogicInterface.getBookDetails(book,model);
        return "book/book-details";
    }
    @GetMapping("/book/add")
    public String addBookPage( Model model) {
        return "book/book-add";
    }
    @PostMapping("/book/add")
    public String addBook(@RequestParam String name, @RequestParam String author, @RequestParam String location,@RequestParam String ISBN,
                          @RequestParam String publicationDate, @RequestParam String modificationDate,
                          @RequestParam String addedDate, Model model) throws ParseException, IOException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date pubDate= format.parse(publicationDate);
        Date addDate= format.parse(addedDate);
        Date modDate= format.parse(modificationDate);
        Book book = new Book(name,ISBN,author,location,pubDate,addDate,modDate);
        bookBusinessLogicInterface.createBook(book);
        return "book/book-done";
    }
    @GetMapping("/book/{id}/edit")
    public String bookEditPage(@PathVariable(value="id") Long id, Model model) {
        if(!bookBusinessLogicInterface.bookExistByID(id)){
            return "redirect:/error";
        }
        Optional<Book> book= bookManagerInterface.findById(id);
        bookBusinessLogicInterface.getBookDetails(book,model);
        return "book/book-edit";
    }
    @PostMapping("/book/{id}/edit")
    public String bookEdit(@PathVariable(value="id") Long id,@RequestParam String name, @RequestParam String author,
                               @RequestParam String location,@RequestParam String ISBN,
                               @RequestParam String publicationDate, @RequestParam String modificationDate,
                               @RequestParam String addedDate, Model model) throws ParseException, IOException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date pubDate= format.parse(publicationDate);
        Date addDate= format.parse(addedDate);
        Date modDate= format.parse(modificationDate);
        Book book = bookManagerInterface.findById(id).orElseThrow();
        book.setName(name);
        book.setAuthor(author);
        book.setLocation(location);
        book.setISBN(ISBN);
        book.setPublicationDate(pubDate);
        book.setAddedDate(addDate);
        book.setModificationDate(modDate);
        bookBusinessLogicInterface.editBook(book,model);
        return "redirect:/book-done";
    }
    @GetMapping("/book-done")
    public String bookDone(Model model){
        return "book/book-done";
    }
    @PostMapping("/book/{id}/remove")
    public String bookDelete(@PathVariable(value="id") Long id, Model model) {
        Book book = bookManagerInterface.findById(id).orElseThrow();
        bookBusinessLogicInterface.deleteBook(book);
        return "redirect:/book-delete";
    }
    @GetMapping("/book-delete")
    public String bookDeleted(Model model){
        return "book/book-delete";
    }

    @PostMapping("/book/search")
    public String bookSearch(@RequestParam String name, Model model){
        Iterable<Book> books= bookManagerInterface.getByName(name);
        bookBusinessLogicInterface.searchBookByName(books,model);
        return "book/book-search";
    }

}