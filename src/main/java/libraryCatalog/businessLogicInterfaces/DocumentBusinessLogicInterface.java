package libraryCatalog.businessLogicInterfaces;

import libraryCatalog.models.Document;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.Optional;

public interface DocumentBusinessLogicInterface {
    void createDoc(Document document) throws IOException;
    void getAllDocs(Iterable<Document> docs,Model model);
    void getDocDetails(Optional<Document> doc, Model model);
    void editDoc(Document document) throws IOException;
    void deleteDoc(Document document);
    boolean docExistByID(Long id);
    void searchDoc(Iterable<Document> documents,Model model);

}
