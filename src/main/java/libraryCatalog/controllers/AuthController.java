package libraryCatalog.controllers;

import libraryCatalog.models.Role;
import libraryCatalog.models.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import libraryCatalog.models.User;
import libraryCatalog.repoInterfaces.UserManagerInterface;
import java.util.Optional;


@Controller
public class AuthController {
    @Autowired
    UserManagerInterface userManagerInterface;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public AuthController()  {
    }
    @GetMapping("/home")
    public String home( Model model) {
        return "home";
    }
    @GetMapping("/register")
    public String reg( Model model) {
        return "Registration";
    }
    @PostMapping("/create-user")
    public String newUser(@RequestParam String login,String pass,String email, Model model) {
        String encodedPassword = passwordEncoder.encode(pass);
        User user= new User(login,encodedPassword,email, Role.USER, Status.ACTIVE);
        userManagerInterface.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String enter( Model model) {
        return "enter";
    }

    @PostMapping("/logout")
    public String logout(Model model){
        return "redirect:/login";
    }
}