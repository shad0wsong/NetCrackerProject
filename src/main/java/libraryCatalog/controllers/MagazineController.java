package libraryCatalog.controllers;

import libraryCatalog.businessLogic.MagazineBusinessLogic;
import libraryCatalog.businessLogicInterfaces.MagazineBusinessLogicInterface;
import libraryCatalog.models.Location;
import libraryCatalog.models.Magazine;
import libraryCatalog.repoInterfaces.LocationManagerInterface;
import libraryCatalog.repoInterfaces.MagazineManagerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
public class MagazineController {
    @Autowired
    MagazineBusinessLogicInterface magazineBusinessLogicInterface;
    @Autowired
    MagazineManagerInterface magazineManagerInterface;
    @Autowired
    LocationManagerInterface locationManagerInterface;

    @GetMapping("/magazine")
    public String allMagazines( Model model) {
        Iterable<Magazine> magazines= magazineManagerInterface.findAll();
        magazineBusinessLogicInterface.getAllMagazine(magazines,model);
        return "magazine/mag-main";
    }
    @GetMapping("/magazine/{id}")
    public String magazineDetails(@PathVariable(value="id") Long id, Model model) {
        if(!magazineBusinessLogicInterface.magazineExistByID(id)){
            return "redirect:/error";
        }
        Optional<Magazine> magazine= magazineManagerInterface.findById(id);
        magazineBusinessLogicInterface.getMagazineDetails(magazine, model);
        return "magazine/mag-details";
    }
    @GetMapping("/magazine/add")
    public String addMagazinePage( Model model) {
        return "magazine/mag-add";
    }
    @PostMapping("/magazine/add")
    public String addMagazine(@RequestParam String name, @RequestParam String location,
                          @RequestParam String publicationDate, @RequestParam String modificationDate,
                          @RequestParam String addedDate, Model model) throws ParseException, IOException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date pubDate= format.parse(publicationDate);
        Date addDate= format.parse(addedDate);
        Date modDate= format.parse(modificationDate);
        Optional<Location> locationOptional=locationManagerInterface.getByName(location);
        Location locationObj=locationOptional.get();
        Magazine magazine = new Magazine(name,locationObj,pubDate,addDate,modDate);
        magazineBusinessLogicInterface.createMagazine(magazine);
        return "magazine/mag-done";
    }
    @GetMapping("/magazine/{id}/edit")
    public String magazineEditPage(@PathVariable(value="id") Long id, Model model) {
        if(!magazineBusinessLogicInterface.magazineExistByID(id)){
            return "redirect:/error";
        }
        Optional<Magazine> magazine= magazineManagerInterface.findById(id);
        magazineBusinessLogicInterface.getMagazineDetails(magazine, model);
        return "magazine/mag-edit";
    }
    @PostMapping("/magazine/{id}/edit")
    public String magazineEdit(@PathVariable(value="id") Long id,@RequestParam String name, @RequestParam String location,
                               @RequestParam String publicationDate, @RequestParam String modificationDate,
                               @RequestParam String addedDate, Model model) throws ParseException, IOException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date pubDate= format.parse(publicationDate);
        Date addDate= format.parse(addedDate);
        Date modDate= format.parse(modificationDate);
        Optional<Location> locationOptional=locationManagerInterface.getByName(location);
        Location locationObj=locationOptional.get();
        Magazine magazine = magazineManagerInterface.findById(id).orElseThrow();
        magazine.setName(name);
        magazine.setLocation(locationObj);
        magazine.setPublicationDate(pubDate);
        magazine.setAddedDate(addDate);
        magazine.setModificationDate(modDate);
        magazineBusinessLogicInterface.editMagazine(magazine);
        return "redirect:/magazine-done";
    }
    @GetMapping("/magazine-done")
    public String magazineDone(Model model){
        return "magazine/mag-done";
    }
    @PostMapping("/magazine/{id}/remove")
    public String magazineDelete(@PathVariable(value="id") Long id, Model model) {
        Magazine magazine = magazineManagerInterface.findById(id).orElseThrow();
        magazineBusinessLogicInterface.deleteMagazine(magazine);
        return "redirect:/magazine-delete";
    }
    @GetMapping("/magazine-delete")
    public String magazineDeleted(Model model){
        return "magazine/mag-delete";
    }

    @PostMapping("/magazine/search")
    public String magazineSearch(@RequestParam String name, Model model){
        Iterable<Magazine> magazines= magazineManagerInterface.getByName(name);
        magazineBusinessLogicInterface.searchMagazineByName(magazines, model);
        return "magazine/mag-search";
    }
}
