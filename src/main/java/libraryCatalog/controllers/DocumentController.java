package libraryCatalog.controllers;

import libraryCatalog.businessLogic.DocumentBusinessLogic;
import libraryCatalog.businessLogicInterfaces.DocumentBusinessLogicInterface;
import libraryCatalog.models.Document;
import libraryCatalog.models.Location;
import libraryCatalog.repoInterfaces.DocumentManagerInterface;
import libraryCatalog.repoInterfaces.LocationManagerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
public class DocumentController {
    @Autowired
    DocumentBusinessLogicInterface documentBusinessLogicInterface;
    @Autowired
    DocumentManagerInterface documentManagerInterface;
    @Autowired
    LocationManagerInterface locationManagerInterface;
    @GetMapping("/document")
    public String allDocs( Model model) {
        Iterable<Document> docs= documentManagerInterface.findAll();
        documentBusinessLogicInterface.getAllDocs(docs,model);
        return "document/doc-main";
    }
    @GetMapping("/document/{id}")
    public String docDetails(@PathVariable(value="id") Long id, Model model) {
        if(!documentBusinessLogicInterface.docExistByID(id)){
            return "redirect:/error";
        }
        Optional<Document> doc= documentManagerInterface.findById(id);
        documentBusinessLogicInterface.getDocDetails(doc,model);
        return "document/doc-details";
    }
    @GetMapping("/document/add")
    public String addDocPage( Model model) {
        return "document/doc-add";
    }
    @PostMapping("/document/add")
    public String addDoc(@RequestParam String name, @RequestParam String documentNumber, @RequestParam String location,
                          @RequestParam String creationDate, @RequestParam String modificationDate,
                          @RequestParam String addedDate, Model model) throws ParseException, IOException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date creatDate= format.parse(creationDate);
        Date addDate= format.parse(addedDate);
        Date modDate= format.parse(modificationDate);
        Optional<Location> locationOptional=locationManagerInterface.getByName(location);
        Location locationObj=locationOptional.get();
        Document document = new Document(name,documentNumber,locationObj,creatDate,addDate,modDate);
        documentBusinessLogicInterface.createDoc(document);
        return "document/doc-done";
    }
    @GetMapping("/document/{id}/edit")
    public String docEditPage(@PathVariable(value="id") Long id, Model model) {
        if(!documentBusinessLogicInterface.docExistByID(id)){
            return "redirect:/error";
        }
        Optional<Document> doc= documentManagerInterface.findById(id);
        documentBusinessLogicInterface.getDocDetails(doc,model);
        return "document/doc-edit";
    }
    @GetMapping("/doc-done")
    public String docDone( Model model) {
        return "document/doc-done";
    }
    @PostMapping("/document/{id}/edit")
    public String DocEdit(@PathVariable(value="id") Long id,@RequestParam String name, @RequestParam String documentNumber, @RequestParam String location,
                               @RequestParam String creationDate, @RequestParam String modificationDate,
                               @RequestParam String addedDate, Model model) throws ParseException, IOException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date creatDate= format.parse(creationDate);
        Date addDate= format.parse(addedDate);
        Date modDate= format.parse(modificationDate);
        Optional<Location> locationOptional=locationManagerInterface.getByName(location);
        Location locationObj=locationOptional.get();
        Document document = documentManagerInterface.findById(id).orElseThrow();
        document.setName(name);
        document.setDocumentNumber(documentNumber);
        document.setLocation(locationObj);
        document.setCreationDate(creatDate);
        document.setAddedDate(addDate);
        document.setModificationDate(modDate);
        documentBusinessLogicInterface.editDoc(document);
        return "redirect:/doc-done";
    }
    @PostMapping("/document/{id}/remove")
    public String docDelete(@PathVariable(value="id") Long id, Model model) {
        Document document = documentManagerInterface.findById(id).orElseThrow();
        documentBusinessLogicInterface.deleteDoc(document);
        return "redirect:/doc-delete";
    }
    @GetMapping("/doc-delete")
    public String docDeleted(Model model){
        return "document/doc-delete";
    }

    @PostMapping("/document/search")
    public String docSearch(@RequestParam String name, Model model){
        Iterable<Document> documents= documentManagerInterface.getByName(name);
        documentBusinessLogicInterface.searchDocByName(documents, model);
        return "document/doc-search";
    }
}
