package libraryCatalog.businessLogic;

import libraryCatalog.businessLogicInterfaces.ObjectBusinessLogicInterface;
import libraryCatalog.models.Object;
import libraryCatalog.repoInterfaces.ObjectManagerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


@Service
public class ObjectBusinessLogic implements ObjectBusinessLogicInterface {
    @Autowired
    ObjectManagerInterface objectManagerInterface;


    public void createObject(Object object) throws IOException {
        objectManagerInterface.save(object);
    }
    public void getAllObject(Iterable<Object> objects, Model model){
        model.addAttribute("objects",objects);
    }
    public void getObjectDetails(Optional<Object> object , Model model){
        ArrayList<Object> res= new ArrayList<>();
        object.ifPresent(res::add);
        model.addAttribute("object",res);
    }
    public void editObject(Object object,Model model) throws IOException {
        objectManagerInterface.save(object);

    }
    public void deleteObject(Object object){
        objectManagerInterface.delete(object);
    }
    public boolean objectExistByID(Long id){
        if(objectManagerInterface.existsById(id)){
            return true ;
        }
        return false;
    }
}
