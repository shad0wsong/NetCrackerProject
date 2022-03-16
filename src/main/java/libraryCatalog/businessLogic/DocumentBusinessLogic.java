package libraryCatalog.businessLogic;

import libraryCatalog.models.Book;
import libraryCatalog.models.Document;
import libraryCatalog.repo.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class DocumentBusinessLogic {
    @Autowired
    DocumentRepository documentRepository;

    public void createDoc(String name, String documentNumber, String location, Date creatDate, Date addDate, Date modDate){
        Document document = new Document(name,documentNumber,location,creatDate,addDate,modDate);
        documentRepository.save(document);
    }
    public void getAllDocs(Model model){
        Iterable<Document> docs= documentRepository.findAll();
        model.addAttribute("docs",docs);
    }
    public void getDocDetails(Long id ,Model model){
        Optional<Document> doc= documentRepository.findById(id);
        ArrayList<Document> res= new ArrayList<>();
        doc.ifPresent(res::add);
        model.addAttribute("doc",res);
    }
    public void editDoc(Long id,  String name,  String documentNumber,  String location,
                         Date creationDate,Date modDate,
                         Date addedDate){
        Document document = documentRepository.findById(id).orElseThrow();
        document.setName(name);
        document.setDocumentNumber(documentNumber);
        document.setLocation(location);
        document.setCreationDate(creationDate);
        document.setAddedDate(addedDate);
        document.setModificationDate(modDate);
        documentRepository.save(document);
    }
    public void deleteDoc(Long id){
        Document document = documentRepository.findById(id).orElseThrow();
        documentRepository.delete(document);
    }
    public boolean docExistByID(Long id){
        if(documentRepository.existsById(id)){
            return true ;
        }
        return false;
    }
    public void searchDocByName(String name,Model model){
        Iterable<Document> documents= documentRepository.getByName(name);
        model.addAttribute("doc",documents);
    }
}
