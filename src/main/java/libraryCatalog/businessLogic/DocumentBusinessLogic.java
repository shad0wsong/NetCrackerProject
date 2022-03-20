package libraryCatalog.businessLogic;

import libraryCatalog.JSONInterfaces.DocJSONInterface;
import libraryCatalog.businessLogicInterfaces.DocumentBusinessLogicInterface;
import libraryCatalog.models.Document;
import libraryCatalog.repoInterfaces.DocumentManagerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;


@Service
public class DocumentBusinessLogic implements DocumentBusinessLogicInterface {
    @Autowired
    DocumentManagerInterface documentManagerInterface;
    @Autowired
    DocJSONInterface docJSONInterface;

    public DocumentBusinessLogic() {
    }

    public void createDoc(Document document) throws IOException {
        documentManagerInterface.save(document);
        Document copy = new Document(document.getName(),document.getDocumentNumber(),
                document.getLocation(),document.getCreationDate(),document.getAddedDate(),document.getModificationDate());
        Long docID= documentManagerInterface.getMaxID();
        copy.setId(docID);
        docJSONInterface.DocToJSONandFile(copy);

    }
    public void getAllDocs(Iterable<Document> docs,Model model){
        model.addAttribute("docs",docs);
    }

    public void getDocDetails(Optional<Document> doc,Model model){
        ArrayList<Document> res= new ArrayList<>();
        doc.ifPresent(res::add);
        model.addAttribute("doc",res);
    }
    public void editDoc(Document document) throws IOException {
        documentManagerInterface.save(document);
        docJSONInterface.DocToJSONandFile(document);

    }
    public void deleteDoc(Document document){
        documentManagerInterface.delete(document);
        docJSONInterface.DeleteJSON(document);
    }
    public boolean docExistByID(Long id){
        if(documentManagerInterface.existsById(id)){
            return true ;
        }
        return false;
    }
    public void searchDoc(Iterable<Document> documents,Model model){
        model.addAttribute("doc",documents);
    }
}
