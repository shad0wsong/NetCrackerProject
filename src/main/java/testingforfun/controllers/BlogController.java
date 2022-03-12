package testingforfun.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import testingforfun.models.Post;
import testingforfun.repo.PostRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {
    @Autowired
    private PostRepository postRepository;
    @GetMapping("/blog")
    public String blog_main( Model model) {
        Iterable<Post> posts= postRepository.findAll();
        model.addAttribute("posts",posts);
        return "blog-parts/blog";
    }
    @GetMapping("/blog/add")
    public String blog_add( Model model) {

        return "blog-parts/blog-add";
    }
    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title,@RequestParam String anons,@RequestParam String full_text, Model model) {
    Post post = new Post(title,anons,full_text);
    postRepository.save(post);
        return "redirect:/blog-done";
    }
    @GetMapping("/blog-done")
    public String blog_done( Model model) {
        return "blog-parts/blog-done";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value="id") Long id, Model model) {
        if(!postRepository.existsById(id)){
            return "redirect:/error/no-post";
        }
        Optional<Post> post= postRepository.findById(id);
        ArrayList<Post> res= new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post",res);
        return "blog-parts/blog-details";
    }
    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value="id") Long id, Model model) {
        if(!postRepository.existsById(id)){
            return "redirect:/error/no-post";
        }
        Optional<Post> post= postRepository.findById(id);
        ArrayList<Post> res= new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post",res);
        return "blog-parts/blog-edit";
    }
    @PostMapping("/blog/{id}/edit")
    public String blogPostEdit(@PathVariable(value="id") Long id,@RequestParam String title,@RequestParam String anons,@RequestParam String full_text, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/blog-done";
    }
    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value="id") Long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog-delete";
    }
    @GetMapping("/blog-delete")
    public String blog_delete( Model model) {
        return "blog-parts/blog-delete";
    }
}
