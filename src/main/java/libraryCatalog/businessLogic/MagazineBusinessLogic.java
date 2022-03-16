package libraryCatalog.businessLogic;

import libraryCatalog.models.Magazine;
import libraryCatalog.models.PatentDocument;
import libraryCatalog.repo.MagazineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class MagazineBusinessLogic {

    @Autowired
    MagazineRepository magazineRepository;

    public void createMagazine(String name, String location,Date pubDate, Date addDate, Date modDate){
        Magazine magazine = new Magazine(name,location,pubDate,addDate,modDate);
        magazineRepository.save(magazine);
    }
    public void getAllMagazine(Model model){
        Iterable<Magazine> magazines= magazineRepository.findAll();
        model.addAttribute("magazines",magazines);
    }
    public void getMagazineDetails(Long id ,Model model){
        Optional<Magazine> magazine= magazineRepository.findById(id);
        ArrayList<Magazine> res= new ArrayList<>();
        magazine.ifPresent(res::add);
        model.addAttribute("magazine",res);
    }
    public void editMagazine(Long id ,String name, String location,Date pubDate, Date addDate, Date modDate){
        Magazine magazine = magazineRepository.findById(id).orElseThrow();
        magazine.setName(name);
        magazine.setLocation(location);
        magazine.setPublicationDate(pubDate);
        magazine.setAddedDate(addDate);
        magazine.setModificationDate(modDate);
        magazineRepository.save(magazine);
    }
    public void deleteMagazine(Long id){
        Magazine magazine = magazineRepository.findById(id).orElseThrow();
        magazineRepository.delete(magazine);
    }
    public boolean magazineExistByID(Long id){
        if(magazineRepository.existsById(id)){
            return true ;
        }
        return false;
    }
    public void searchMagazineByName(String name,Model model){
        Iterable<Magazine> magazines= magazineRepository.getByName(name);
        model.addAttribute("mag",magazines);
    }

}
