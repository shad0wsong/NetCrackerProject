package libraryCatalog.businessLogicInterfaces;

import libraryCatalog.models.Object;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.Optional;

public interface ObjectBusinessLogicInterface {
    void createObject(Object object) throws IOException;
    void getAllObject(Iterable<Object> objects, Model model);
    void getObjectDetails(Optional<Object> object, Model model);
    void editObject(Object object,Model model) throws IOException;
    void deleteObject(Object object);
    boolean objectExistByID(Long id);
}
