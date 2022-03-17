package libraryCatalog.businessLogic;

import libraryCatalog.JSONInterfaces.MagazineJSONInterface;
import libraryCatalog.businessLogicInterfaces.MagazineBusinessLogicInterface;
import libraryCatalog.models.Magazine;
import libraryCatalog.models.PatentDocument;
import libraryCatalog.repoInterfaces.MagazineManagerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class MagazineBusinessLogic implements MagazineBusinessLogicInterface {

    @Autowired
    MagazineManagerInterface magazineManagerInterface;

    @Autowired
    MagazineJSONInterface magazineJSONInterface;

    public void createMagazine(Magazine magazine) throws IOException {
        magazineManagerInterface.save(magazine);
        Magazine copy = new Magazine(magazine.getName(),
                magazine.getLocation(),magazine.getPublicationDate(),magazine.getAddedDate(),magazine.getModificationDate());
        Long magazineID= magazineManagerInterface.getMaxID();
        copy.setId(magazineID);
        magazineJSONInterface.MagazineToJSONandFile(copy);
    }
    public void getAllMagazine(Iterable<Magazine> magazines,Model model){
        model.addAttribute("magazines",magazines);
    }
    public void getMagazineDetails(Optional<Magazine> magazine ,Model model){
        ArrayList<Magazine> res= new ArrayList<>();
        magazine.ifPresent(res::add);
        model.addAttribute("magazine",res);
    }
    public void editMagazine(Magazine magazine) throws IOException {
        magazineManagerInterface.save(magazine);
        magazineJSONInterface.MagazineToJSONandFile(magazine);
    }
    public void deleteMagazine(Magazine magazine){
        magazineManagerInterface.delete(magazine);
        magazineJSONInterface.DeleteJSON(magazine);
    }
    public boolean magazineExistByID(Long id){
        if(magazineManagerInterface.existsById(id)){
            return true ;
        }
        return false;
    }
    public void searchMagazineByName(Iterable<Magazine> magazines,Model model){
        model.addAttribute("mag",magazines);
    }

}
