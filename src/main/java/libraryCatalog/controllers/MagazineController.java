package libraryCatalog.controllers;

import libraryCatalog.businessLogic.MagazineBusinessLogic;
import libraryCatalog.models.Magazine;
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
    MagazineBusinessLogic magazineBusinessLogic;
    @GetMapping("/magazine")
    public String allMagazines( Model model) {
        magazineBusinessLogic.getAllMagazine(model);
        return "magazine/mag-main";
    }
    @GetMapping("/magazine/{id}")
    public String magazineDetails(@PathVariable(value="id") Long id, Model model) {
        if(!magazineBusinessLogic.magazineExistByID(id)){
            return "redirect:/error";
        }
        magazineBusinessLogic.getMagazineDetails(id, model);
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
        magazineBusinessLogic.createMagazine(name,location,pubDate,addDate,modDate);
        return "magazine/mag-done";
    }
    @GetMapping("/magazine/{id}/edit")
    public String magazineEditPage(@PathVariable(value="id") Long id, Model model) {
        if(!magazineBusinessLogic.magazineExistByID(id)){
            return "redirect:/error";
        }
        magazineBusinessLogic.getMagazineDetails(id, model);
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
        magazineBusinessLogic.editMagazine(id,name,location,pubDate,addDate,modDate);
        return "redirect:/magazine-done";
    }
    @GetMapping("/magazine-done")
    public String magazineDone(Model model){
        return "magazine/mag-done";
    }
    @PostMapping("/magazine/{id}/remove")
    public String magazineDelete(@PathVariable(value="id") Long id, Model model) {
        magazineBusinessLogic.deleteMagazine(id);
        return "redirect:/magazine-delete";
    }
    @GetMapping("/magazine-delete")
    public String magazineDeleted(Model model){
        return "magazine/mag-delete";
    }

    @PostMapping("/magazine/search")
    public String magazineSearch(@RequestParam String name, Model model){
        magazineBusinessLogic.searchMagazineByName(name, model);
        return "magazine/mag-search";
    }
}
