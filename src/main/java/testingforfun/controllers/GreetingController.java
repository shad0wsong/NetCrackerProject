package testingforfun.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import testingforfun.models.Users;
import testingforfun.repo.UserRepository;
import java.util.Optional;


@Controller
public class GreetingController  {
    @Autowired
    UserRepository userRepository;
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
        Optional<Users> user= userRepository.findByLogin(login);
        if(user.isPresent()){
            Users checkUser =user.get();
            if(checkUser.getPass().equals(pass)){
                System.out.println("access granted to "+checkUser.getLogin());
                return "/home";            }
        }
        return "redirect:/error";
    }
    @PostMapping("/create-user")
    public String newUser(@RequestParam String login,String pass,String email, Model model) {
        Users user= new Users(login,pass,email);
        userRepository.save(user);
        return "redirect:/enter";
    }

    @GetMapping("/enter")
    public String enter( Model model) {
        return "enter";
    }
}