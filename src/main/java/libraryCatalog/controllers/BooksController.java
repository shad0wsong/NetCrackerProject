package libraryCatalog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import libraryCatalog.models.Books;
import libraryCatalog.repo.BookRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Controller
public class BooksController {
    @Autowired
    BookRepository bookRepository;
    @GetMapping("/book")
    public String allBooks( Model model) {
        Iterable<Books> books= bookRepository.findAll();
        model.addAttribute("books",books);
        return "book/book-main";
    }
    @GetMapping("/book/{id}")
    public String bookDetails(@PathVariable(value="id") Long id, Model model) {
        if(!bookRepository.existsById(id)){
            return "redirect:/error";
        }
        Optional<Books> book= bookRepository.findById(id);
        ArrayList<Books> res= new ArrayList<>();
        book.ifPresent(res::add);
        model.addAttribute("book",res);
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
        Books book = new Books(name,ISBN,author,location,pubDate,addDate,modDate);
        bookRepository.save(book);
        return "book/book-done";
    }
    @GetMapping("/book/{id}/edit")
    public String bookEditPage(@PathVariable(value="id") Long id, Model model) {
        if(!bookRepository.existsById(id)){
            return "redirect:/error";
        }
        Optional<Books> book= bookRepository.findById(id);
        ArrayList<Books> res= new ArrayList<>();
        book.ifPresent(res::add);
        model.addAttribute("book",res);
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
        Books book = bookRepository.findById(id).orElseThrow();
        book.setName(name);
        book.setAuthor(author);
        book.setLocation(location);
        book.setISBN(ISBN);
        book.setPublicationDate(pubDate);
        book.setAddedDate(addDate);
        book.setModificationDate(modDate);
        bookRepository.save(book);
        return "redirect:/book-done";
    }
    @GetMapping("/book-done")
    public String bookDone(Model model){
        return "book/book-done";
    }
    @PostMapping("/book/{id}/remove")
    public String bookDelete(@PathVariable(value="id") Long id, Model model) {
        Books book = bookRepository.findById(id).orElseThrow();
        bookRepository.delete(book);
        return "redirect:/book-delete";
    }
    @GetMapping("/book-delete")
    public String bookDeleted(Model model){
        return "book/book-delete";
    }

    @PostMapping("/book/search")
    public String bookSearch(@RequestParam String name, Model model){
        Iterable<Books> books= bookRepository.getByName(name);
        model.addAttribute("book",books);
        return "book/book-search";
    }

}
