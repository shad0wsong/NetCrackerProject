package libraryCatalog.businessLogic;

import libraryCatalog.JSONInterfaces.PatentDocJSONInterface;
import libraryCatalog.businessLogicInterfaces.PatentDocumentBusinessLogicInterface;
import libraryCatalog.models.Book;
import libraryCatalog.models.PatentDocument;
import libraryCatalog.repoInterfaces.PatentDocumentManagerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class PatentDocumentBusinessLogic implements PatentDocumentBusinessLogicInterface {
    @Autowired
    PatentDocumentManagerInterface patentDocumentManagerInterface;
    @Autowired
    PatentDocJSONInterface patentDocJSONInterface;
    public void createPatDoc(PatentDocument patentDocument) throws IOException {
        patentDocumentManagerInterface.save(patentDocument);
        PatentDocument copy = new PatentDocument(patentDocument.getName(),patentDocument.getPatentNumber(),patentDocument.getAuthor(),
                patentDocument.getLocation(),patentDocument.getAddedDate(),patentDocument.getModificationDate());
        Long docID= patentDocumentManagerInterface.getMaxID();
        copy.setId(docID);
        patentDocJSONInterface.PatentDocToJSONandFile(copy);
    }
    public void getAllPatDoc(Iterable<PatentDocument> patDocs,Model model){
        model.addAttribute("patDocs",patDocs);
    }
    public void getPatDocDetails(Optional<PatentDocument> patentDocument ,Model model){
        ArrayList<PatentDocument> res= new ArrayList<>();
        patentDocument.ifPresent(res::add);
        model.addAttribute("patDoc",res);
    }
    public void editPatDoc(PatentDocument patentDocument) throws IOException {
        patentDocumentManagerInterface.save(patentDocument);
        patentDocJSONInterface.PatentDocToJSONandFile(patentDocument);
    }
    public void deletePatDoc(PatentDocument patentDocument){
        patentDocumentManagerInterface.delete(patentDocument);
        patentDocJSONInterface.DeleteJSON(patentDocument);
    }
    public boolean patDocExistByID(Long id){
        if(patentDocumentManagerInterface.existsById(id)){
            return true ;
        }
        return false;
    }
    public void searchPatDoc(Iterable<PatentDocument> documents,Model model){
        model.addAttribute("patDoc",documents);
    }
}
