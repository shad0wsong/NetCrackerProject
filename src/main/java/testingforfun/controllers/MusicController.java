package testingforfun.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import testingforfun.models.Music;
import testingforfun.models.Post;
import testingforfun.repo.MusicRepository;

@Controller
public class MusicController {
    @Autowired
    MusicRepository musicRepository;
    @GetMapping("/music")
     public String allMusic(Model model){
        Iterable<Music> music= musicRepository.findAll();
        model.addAttribute("music",music);
         return "music-parts/music";
     }

}
