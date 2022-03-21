package libraryCatalog.controllers;

import libraryCatalog.businessLogic.MagazineBusinessLogic;
import libraryCatalog.businessLogicInterfaces.MagazineBusinessLogicInterface;
import libraryCatalog.models.Document;
import libraryCatalog.models.Location;
import libraryCatalog.models.Magazine;
import libraryCatalog.repoInterfaces.LocationManagerInterface;
import libraryCatalog.repoInterfaces.MagazineManagerInterface;
import org.apache.log4j.Logger;
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
    static Logger log = Logger.getLogger(MagazineController.class.getName());
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
            log.error("Magazine not found ");
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
        if(!locationOptional.isPresent()){
            log.error("Magazine not found ");
            return "redirect:/error";
        }
        Location locationObj=locationOptional.get();
        Magazine magazine = new Magazine(name,locationObj,pubDate,addDate,modDate);
        magazineBusinessLogicInterface.createMagazine(magazine);
        log.info("Magazine added");
        return "magazine/mag-done";
    }
    @GetMapping("/magazine/{id}/edit")
    public String magazineEditPage(@PathVariable(value="id") Long id, Model model) {
        if(!magazineBusinessLogicInterface.magazineExistByID(id)){
            log.error("Magazine not found ");
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
        if(!locationOptional.isPresent()){
            log.error("Magazine not found ");
            return "redirect:/error";
        }
        Location locationObj=locationOptional.get();
        Magazine magazine = magazineManagerInterface.findById(id).orElseThrow();
        magazine.setName(name);
        magazine.setLocation(locationObj);
        magazine.setPublicationDate(pubDate);
        magazine.setAddedDate(addDate);
        magazine.setModificationDate(modDate);
        magazineBusinessLogicInterface.editMagazine(magazine);
        log.info("Magazine updated");
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
        log.info("Magazine deleted");
        return "redirect:/magazine-delete";
    }
    @GetMapping("/magazine-delete")
    public String magazineDeleted(Model model){
        return "magazine/mag-delete";
    }

    @GetMapping("magazine/parametrSearch")
    public String magParametrSearchPage(Model model){
        return "magazine/mag-parametrSearch";
    }
    @PostMapping("magazine/parametrSearch")
    public String magParametrSearch(@RequestParam String name,
                                    @RequestParam String location,@RequestParam String publicationDate, @RequestParam String modificationDate,
                                    @RequestParam String addedDate, Model model) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date pubDate;
        Date modDate;
        Date addDate;

        if(!publicationDate.equals("")){
            pubDate= format.parse(publicationDate);
        }
        else pubDate =null;

        if(!modificationDate.equals("")){
            modDate= format.parse(modificationDate);
        }
        else modDate=null;
        if(!addedDate.equals("")){
            addDate= format.parse(addedDate);
        }
        else addDate=null;
        Location locationObj=locationManagerInterface.getByNameORNULL(location);
        Iterable<Magazine> mag = magazineManagerInterface.parametrSearch(name,locationObj,pubDate,modDate,addDate);
        magazineBusinessLogicInterface.searchMagazine(mag,model);
        return "magazine/mag-search";
    }
}
