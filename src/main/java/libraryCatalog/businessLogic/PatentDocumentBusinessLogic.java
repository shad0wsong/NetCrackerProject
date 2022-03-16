package libraryCatalog.businessLogic;

import libraryCatalog.models.Book;
import libraryCatalog.models.PatentDocument;
import libraryCatalog.repo.BookRepository;
import libraryCatalog.repo.PatentDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class PatentDocumentBusinessLogic {
    @Autowired
    PatentDocumentRepository patentDocumentRepository;

    public void createPatDoc(String name,String patentNumber,String author,String location, Date addDate,Date modDate){
        PatentDocument patentDocument = new PatentDocument(name,patentNumber,author,location,addDate,modDate);
        patentDocumentRepository.save(patentDocument);
    }
    public void getAllPatDoc(Model model){
        Iterable<PatentDocument> patDocs= patentDocumentRepository.findAll();
        model.addAttribute("patDocs",patDocs);
    }
    public void getPatDocDetails(Long id ,Model model){
        Optional<PatentDocument> patentDocument= patentDocumentRepository.findById(id);
        ArrayList<PatentDocument> res= new ArrayList<>();
        patentDocument.ifPresent(res::add);
        model.addAttribute("patDoc",res);
    }
    public void editPatDoc(Long id ,String name, String patentNumber, String author, String location,Date addDate,Date modDate){
        PatentDocument patentDocument = patentDocumentRepository.findById(id).orElseThrow();
        patentDocument.setName(name);
        patentDocument.setAuthor(author);
        patentDocument.setLocation(location);
        patentDocument.setPatentNumber(patentNumber);
        patentDocument.setAddedDate(addDate);
        patentDocument.setModificationDate(modDate);
        patentDocumentRepository.save(patentDocument);
    }
    public void deletePatDoc(Long id){
        PatentDocument patentDocument = patentDocumentRepository.findById(id).orElseThrow();
        patentDocumentRepository.delete(patentDocument);
    }
    public boolean patDocExistByID(Long id){
        if(patentDocumentRepository.existsById(id)){
            return true ;
        }
        return false;
    }
    public void searchPatDocByName(String name,Model model){
        Iterable<PatentDocument> documents= patentDocumentRepository.getByName(name);
        model.addAttribute("patDoc",documents);
    }
}
