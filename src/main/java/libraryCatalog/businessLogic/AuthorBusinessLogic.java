package libraryCatalog.businessLogic;

import libraryCatalog.businessLogicInterfaces.AuthorBusinessLogicInterface;
import libraryCatalog.models.Author;
import libraryCatalog.repoInterfaces.AuthorManagerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AuthorBusinessLogic implements AuthorBusinessLogicInterface {
    @Autowired
    AuthorManagerInterface authorManagerInterface;
    public void createAuthor(Author author) {
        authorManagerInterface.save(author);
    }
    public void getAllAuthor(Iterable<Author> authors, Model model){
        model.addAttribute("authors",authors);
    }
    public void getAuthorDetails(Optional<Author> author , Model model){
        ArrayList<Author> res= new ArrayList<>();
        author.ifPresent(res::add);
        model.addAttribute("author",res);
    }
    public void deleteAuthor(Author author){
        authorManagerInterface.delete(author);

    }
    public boolean authorExistByID(Long id){
        if(authorManagerInterface.existsById(id)){
            return true ;
        }
        return false;
    }
}
