package libraryCatalog.controllers;

import libraryCatalog.businessLogicInterfaces.AuthorBusinessLogicInterface;
import libraryCatalog.models.Author;
import libraryCatalog.repoInterfaces.AuthorManagerInterface;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthorController {
    static Logger log = Logger.getLogger(AuthorController.class.getName());
    @Autowired
    AuthorManagerInterface authorManagerInterface;
    @Autowired
    AuthorBusinessLogicInterface authorBusinessLogicInterface;
    @GetMapping("/author")
    public String allAuthor( Model model) {
        Iterable<Author> authors= authorManagerInterface.findAll();
        authorBusinessLogicInterface.getAllAuthor(authors,model);
        return "author/author-main";
    }
    @GetMapping("/author/{id}")
    public String authorDetails(@PathVariable(value="id") Long id, Model model) {
        if(!authorBusinessLogicInterface.authorExistByID(id)){
            log.error("Author not found ");
            return "redirect:/error";
        }
        Optional<Author> author= authorManagerInterface.findById(id);
        authorBusinessLogicInterface.getAuthorDetails(author,model);
        return "author/author-details";
    }

    @PreAuthorize("hasAuthority('write')")
    @GetMapping("/author/add")
    public String addAuthorPage( Model model) {
        return "author/author-add";
    }

    @PreAuthorize("hasAuthority('write')")
    @PostMapping("/author/add")
    public String addAuthor(@RequestParam String name, @RequestParam String shortBiography, Model model)  {
        Author author = new Author(name, shortBiography);
        authorBusinessLogicInterface.createAuthor(author);
        log.info("Author added");
        return "author/author-done";
    }

    @PreAuthorize("hasAuthority('write')")
    @GetMapping("/author/{id}/edit")
    public String authorEditPage(@PathVariable(value="id") Long id, Model model) {
        if(!authorBusinessLogicInterface.authorExistByID(id)){
            log.error("Author not found ");
            return "redirect:/error";
        }
        Optional<Author> author= authorManagerInterface.findById(id);
        authorBusinessLogicInterface.getAuthorDetails(author,model);
        return "author/author-edit";
    }

    @PreAuthorize("hasAuthority('write')")
    @PostMapping("/author/{id}/edit")
    public String authorEdit(@PathVariable(value="id") Long id, @RequestParam String name, @RequestParam String shortBiography,
                               Model model)  {
        Author author = authorManagerInterface.findById(id).orElseThrow();
        author.setName(name);
        author.setShortBiography(shortBiography);
        authorBusinessLogicInterface.createAuthor(author);
        log.info("Author updated ");
        return "redirect:/author-done";
    }
    @GetMapping("/author-done")
    public String authorDone(Model model){
        return "author/author-done";
    }

    @PreAuthorize("hasAuthority('write')")
    @PostMapping("/author/{id}/remove")
    public String authorDelete(@PathVariable(value="id") Long id, Model model) {
        Author author = authorManagerInterface.findById(id).orElseThrow();
        authorBusinessLogicInterface.deleteAuthor(author);
        log.info("author deleted");
        return "redirect:/author-delete";
    }
    @GetMapping("/author-delete")
    public String authorDeleted(Model model){
        return "author/author-delete";
    }
}
