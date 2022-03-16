package libraryCatalog.controllers;

import libraryCatalog.businessLogic.BookBusinessLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class BookController {
    @Autowired
    BookBusinessLogic bookBusinessLogic;
    @GetMapping("/book")
    public String allBooks( Model model) {
        bookBusinessLogic.getAllBooks(model);
        return "book/book-main";
    }
    @GetMapping("/book/{id}")
    public String bookDetails(@PathVariable(value="id") Long id, Model model) {
        if(!bookBusinessLogic.bookExistByID(id)){
            return "redirect:/error";
        }
        bookBusinessLogic.getBookDetails(id,model);
        return "book/book-details";
    }
    @GetMapping("/book/add")
    public String addBookPage( Model model) {
        return "book/book-add";
    }
    @PostMapping("/book/add")
    public String addBook(@RequestParam String name, @RequestParam String author, @RequestParam String location,@RequestParam String ISBN,
                          @RequestParam String publicationDate, @RequestParam String modificationDate,
                          @RequestParam String addedDate, Model model) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date pubDate= format.parse(publicationDate);
        Date addDate= format.parse(addedDate);
        Date modDate= format.parse(modificationDate);
        bookBusinessLogic.createBook(name,ISBN,author,location,pubDate,addDate,modDate);
        return "book/book-done";
    }
    @GetMapping("/book/{id}/edit")
    public String bookEditPage(@PathVariable(value="id") Long id, Model model) {
        if(!bookBusinessLogic.bookExistByID(id)){
            return "redirect:/error";
        }
        bookBusinessLogic.getBookDetails(id,model);
        return "book/book-edit";
    }
    @PostMapping("/book/{id}/edit")
    public String bookEdit(@PathVariable(value="id") Long id,@RequestParam String name, @RequestParam String author,
                               @RequestParam String location,@RequestParam String ISBN,
                               @RequestParam String publicationDate, @RequestParam String modificationDate,
                               @RequestParam String addedDate, Model model) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date pubDate= format.parse(publicationDate);
        Date addDate= format.parse(addedDate);
        Date modDate= format.parse(modificationDate);
        bookBusinessLogic.editBook(id,name,ISBN,author,location,pubDate,addDate,modDate);
        return "redirect:/book-done";
    }
    @GetMapping("/book-done")
    public String bookDone(Model model){
        return "book/book-done";
    }
    @PostMapping("/book/{id}/remove")
    public String bookDelete(@PathVariable(value="id") Long id, Model model) {
        bookBusinessLogic.deleteBook(id);
        return "redirect:/book-delete";
    }
    @GetMapping("/book-delete")
    public String bookDeleted(Model model){
        return "book/book-delete";
    }

    @PostMapping("/book/search")
    public String bookSearch(@RequestParam String name, Model model){
        bookBusinessLogic.searchBookByName(name,model);
        return "book/book-search";
    }

}
