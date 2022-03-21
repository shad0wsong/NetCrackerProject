package libraryCatalog.controllers;

import libraryCatalog.businessLogic.DocumentBusinessLogic;
import libraryCatalog.businessLogicInterfaces.DocumentBusinessLogicInterface;
import libraryCatalog.models.Author;
import libraryCatalog.models.Book;
import libraryCatalog.models.Document;
import libraryCatalog.models.Location;
import libraryCatalog.repoInterfaces.DocumentManagerInterface;
import libraryCatalog.repoInterfaces.LocationManagerInterface;
import org.apache.log4j.Logger;
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
    static Logger log = Logger.getLogger(DocumentController.class.getName());
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
            log.error("Document not found ");
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
        if(!locationOptional.isPresent()){
            log.error("Document not found");
            return "redirect:/error";
        }
        Location locationObj=locationOptional.get();
        Document document = new Document(name,documentNumber,locationObj,creatDate,addDate,modDate);
        documentBusinessLogicInterface.createDoc(document);
        log.info("Document added ");
        return "document/doc-done";
    }
    @GetMapping("/document/{id}/edit")
    public String docEditPage(@PathVariable(value="id") Long id, Model model) {
        if(!documentBusinessLogicInterface.docExistByID(id)){
            log.error("Document not found ");
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
        if(!locationOptional.isPresent()){
            log.error("Document not found ");
            return "redirect:/error";
        }
        Location locationObj=locationOptional.get();
        Document document = documentManagerInterface.findById(id).orElseThrow();
        document.setName(name);
        document.setDocumentNumber(documentNumber);
        document.setLocation(locationObj);
        document.setCreationDate(creatDate);
        document.setAddedDate(addDate);
        document.setModificationDate(modDate);
        documentBusinessLogicInterface.editDoc(document);
        log.info("Document updated ");
        return "redirect:/doc-done";
    }
    @PostMapping("/document/{id}/remove")
    public String docDelete(@PathVariable(value="id") Long id, Model model) {
        Document document = documentManagerInterface.findById(id).orElseThrow();
        documentBusinessLogicInterface.deleteDoc(document);
        log.info("Document deleted ");
        return "redirect:/doc-delete";
    }
    @GetMapping("/doc-delete")
    public String docDeleted(Model model){
        return "document/doc-delete";
    }

    @GetMapping("document/parametrSearch")
    public String docParametrSearchPage(Model model){
        return "document/doc-parametrSearch";
    }
    @PostMapping("document/parametrSearch")
    public String docParametrSearch(@RequestParam String name, @RequestParam String documentNumber,
                                     @RequestParam String location,
                                     @RequestParam String creationDate, @RequestParam String modificationDate,
                                     @RequestParam String addedDate, Model model) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date creatDate;
        Date modDate;
        Date addDate;
        if(documentNumber.equals("")) documentNumber=null;

        if(!creationDate.equals("")){
            creatDate= format.parse(creationDate);
        }
        else creatDate =null;

        if(!modificationDate.equals("")){
            modDate= format.parse(modificationDate);
        }
        else modDate=null;
        if(!addedDate.equals("")){
            addDate= format.parse(addedDate);
        }
        else addDate=null;
        Location locationObj=locationManagerInterface.getByNameORNULL(location);
        Iterable<Document> doc = documentManagerInterface.parametrSearch(name,documentNumber,locationObj,creatDate,modDate,addDate);
        documentBusinessLogicInterface.searchDoc(doc,model);
        return "document/doc-search";
    }
}
