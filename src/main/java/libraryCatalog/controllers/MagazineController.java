package libraryCatalog.controllers;

import libraryCatalog.models.Books;
import libraryCatalog.models.Documents;
import libraryCatalog.models.Magazines;
import libraryCatalog.repo.MagazineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Controller
public class MagazineController {
    @Autowired
    MagazineRepository magazineRepository;
    @GetMapping("/magazine")
    public String allMagazines( Model model) {
        Iterable<Magazines> magazines= magazineRepository.findAll();
        model.addAttribute("magazines",magazines);
        return "magazine/mag-main";
    }
    @GetMapping("/magazine/{id}")
    public String magazineDetails(@PathVariable(value="id") Long id, Model model) {
        if(!magazineRepository.existsById(id)){
            return "redirect:/error";
        }
        Optional<Magazines> magazine= magazineRepository.findById(id);
        ArrayList<Magazines> res= new ArrayList<>();
        magazine.ifPresent(res::add);
        model.addAttribute("magazine",res);
        return "magazine/mag-details";
    }
    @GetMapping("/magazine/add")
    public String addMagazinePage( Model model) {
        return "magazine/mag-add";
    }
    @PostMapping("/magazine/add")
    public String addMagazine(@RequestParam String name, @RequestParam String location,
                          @RequestParam String publicationDate, @RequestParam String modificationDate,
                          @RequestParam String addedDate, Model model) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date pubDate= format.parse(publicationDate);
        Date addDate= format.parse(addedDate);
        Date modDate= format.parse(modificationDate);
        Magazines magazine = new Magazines(name,location,pubDate,addDate,modDate);
        magazineRepository.save(magazine);
        return "magazine/mag-done";
    }
    @GetMapping("/magazine/{id}/edit")
    public String magazineEditPage(@PathVariable(value="id") Long id, Model model) {
        if(!magazineRepository.existsById(id)){
            return "redirect:/error";
        }
        Optional<Magazines> magazine= magazineRepository.findById(id);
        ArrayList<Magazines> res= new ArrayList<>();
        magazine.ifPresent(res::add);
        model.addAttribute("magazine",res);
        return "magazine/mag-edit";
    }
    @PostMapping("/magazine/{id}/edit")
    public String magazineEdit(@PathVariable(value="id") Long id,@RequestParam String name, @RequestParam String location,
                               @RequestParam String publicationDate, @RequestParam String modificationDate,
                               @RequestParam String addedDate, Model model) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date pubDate= format.parse(publicationDate);
        Date addDate= format.parse(addedDate);
        Date modDate= format.parse(modificationDate);
        Magazines magazine = magazineRepository.findById(id).orElseThrow();
        magazine.setName(name);
        magazine.setLocation(location);
        magazine.setPublicationDate(pubDate);
        magazine.setAddedDate(addDate);
        magazine.setModificationDate(modDate);
        magazineRepository.save(magazine);
        return "redirect:/magazine-done";
    }
    @GetMapping("/magazine-done")
    public String magazineDone(Model model){
        return "magazine/mag-done";
    }
    @PostMapping("/magazine/{id}/remove")
    public String magazineDelete(@PathVariable(value="id") Long id, Model model) {
        Magazines magazine = magazineRepository.findById(id).orElseThrow();
        magazineRepository.delete(magazine);
        return "redirect:/magazine-delete";
    }
    @GetMapping("/magazine-delete")
    public String magazineDeleted(Model model){
        return "magazine/mag-delete";
    }

    @PostMapping("/magazine/search")
    public String magazineSearch(@RequestParam String name, Model model){
        Iterable<Magazines> magazines= magazineRepository.getByName(name);
        model.addAttribute("mag",magazines);
        return "magazine/mag-search";
    }
}
