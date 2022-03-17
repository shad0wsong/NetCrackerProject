package libraryCatalog.businessLogicInterfaces;

import libraryCatalog.models.PatentDocument;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.Optional;

public interface PatentDocumentBusinessLogicInterface {
     void createPatDoc(PatentDocument patentDocument) throws IOException;
     void getAllPatDoc(Iterable<PatentDocument> patDocs, Model model);
     void getPatDocDetails(Optional<PatentDocument> patentDocument , Model model);
     void editPatDoc(PatentDocument patentDocument) throws IOException;
     void deletePatDoc(PatentDocument patentDocument);
    boolean patDocExistByID(Long id);
    public void searchPatDocByName(Iterable<PatentDocument> documents,Model model);

}
