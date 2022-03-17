package libraryCatalog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import libraryCatalog.models.User;
import libraryCatalog.repoInterfaces.UserManagerInterface;
import java.util.Optional;


@Controller
public class GreetingController  {
    @Autowired
    UserManagerInterface userManagerInterface;
    public GreetingController()  {
    }
    @GetMapping("/xd")
    public String xd( Model model) {
        return "xdd";
    }
    @GetMapping("/home")
    public String home( Model model) {
        return "home";
    }
    @GetMapping("/register")
    public String reg( Model model) {
        return "Registration";
    }
    @PostMapping("/check-acc")
    public String checkUser(@RequestParam String login,String pass, Model model) {
        Optional<User> user= userManagerInterface.findByLogin(login);
        if(user.isPresent()){
            User checkUser =user.get();
            if(checkUser.getPass().equals(pass)){
                System.out.println("access granted to "+checkUser.getLogin());
                return "/home";            }
        }
        return "redirect:/error";
    }
    @PostMapping("/create-user")
    public String newUser(@RequestParam String login,String pass,String email, Model model) {
        User user= new User(login,pass,email);
        userManagerInterface.save(user);
        return "redirect:/enter";
    }

    @GetMapping("/enter")
    public String enter( Model model) {
        return "enter";
    }
}