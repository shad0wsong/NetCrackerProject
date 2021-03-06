package libraryCatalog.controllers;

import libraryCatalog.businessLogic.PatentDocumentBusinessLogic;
import libraryCatalog.businessLogicInterfaces.PatentDocumentBusinessLogicInterface;
import libraryCatalog.models.Author;
import libraryCatalog.models.Book;
import libraryCatalog.models.Location;
import libraryCatalog.models.PatentDocument;
import libraryCatalog.repoInterfaces.AuthorManagerInterface;
import libraryCatalog.repoInterfaces.LocationManagerInterface;
import libraryCatalog.repoInterfaces.PatentDocumentManagerInterface;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class PatentDocumentController {
    static Logger log = Logger.getLogger(PatentDocumentController.class.getName());
    @Autowired
    PatentDocumentBusinessLogicInterface patentDocumentBusinessLogicInterface;
    @Autowired
    PatentDocumentManagerInterface patentDocumentManagerInterface;
    @Autowired
    LocationManagerInterface locationManagerInterface;
    @Autowired
    AuthorManagerInterface authorManagerInterface;
    @GetMapping("/patentDoc")
    public String allPatentDoc( Model model) {
        Iterable<PatentDocument> patDocs= patentDocumentManagerInterface.findAll();
        patentDocumentBusinessLogicInterface.getAllPatDoc(patDocs,model);
        return "patentDoc/patentDoc-main";
    }
    @GetMapping("/patentDoc/{id}")
    public String patentDocDetails(@PathVariable(value="id") Long id, Model model) {
        if(!patentDocumentBusinessLogicInterface.patDocExistByID(id)){
            log.error("PatentDocument not found ");
            return "redirect:/error";
        }
        Optional<PatentDocument> patentDocument= patentDocumentManagerInterface.findById(id);
        patentDocumentBusinessLogicInterface.getPatDocDetails(patentDocument, model);
        return "patentDoc/patentDoc-details";
    }
    @PreAuthorize("hasAuthority('write')")
    @GetMapping("/patentDoc/add")
    public String addPatentDocPage( Model model) {
        return "patentDoc/patentDoc-add";
    }
    @PreAuthorize("hasAuthority('write')")
    @PostMapping("/patentDoc/add")
    public String addPatentDoc(@RequestParam String name, @RequestParam String author, @RequestParam String location,
                               @RequestParam String patentNumber,
                               @RequestParam String modificationDate,
                               @RequestParam String addedDate, Model model) throws ParseException, IOException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date addDate= format.parse(addedDate);
        Date modDate= format.parse(modificationDate);
        Optional<Location> locationOptional=locationManagerInterface.getByName(location);
        if(!locationOptional.isPresent()){
            log.error("PatentDocument not found ");
            return "redirect:/error";
        }
        Location locationObj=locationOptional.get();
        Optional<Author> authorOptional=authorManagerInterface.getByName(author);
        if(!authorOptional.isPresent()){
            log.error("PatentDocument not found ");
            return "redirect:/error";
        }
        Author authorObj=authorOptional.get();
        PatentDocument patentDocument = new PatentDocument(name,patentNumber,authorObj,locationObj,addDate,modDate);
        patentDocumentBusinessLogicInterface.createPatDoc(patentDocument);
        log.info("PatentDocument added ");
        return "patentDoc/patentDoc-done";
    }
    @PreAuthorize("hasAuthority('write')")
    @GetMapping("/patentDoc/{id}/edit")
    public String patentDocEditPage(@PathVariable(value="id") Long id, Model model) {
        if(!patentDocumentBusinessLogicInterface.patDocExistByID(id)){
            return "redirect:/error";
        }
        Optional<PatentDocument> patentDocument= patentDocumentManagerInterface.findById(id);
        patentDocumentBusinessLogicInterface.getPatDocDetails(patentDocument, model);
        return "patentDoc/patentDoc-edit";
    }
    @PreAuthorize("hasAuthority('write')")
    @PostMapping("/patentDoc/{id}/edit")
    public String patentDocEdit(@PathVariable(value="id") Long id,@RequestParam String name, @RequestParam String author,
                               @RequestParam String location,@RequestParam String patentNumber,
                               @RequestParam String modificationDate, @RequestParam String addedDate, Model model) throws ParseException, IOException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date addDate= format.parse(addedDate);
        Date modDate= format.parse(modificationDate);
        Optional<Location> locationOptional=locationManagerInterface.getByName(location);
        if(!locationOptional.isPresent()){
            return "redirect:/error";
        }
        Location locationObj=locationOptional.get();
        Optional<Author> authorOptional=authorManagerInterface.getByName(author);
        if(!authorOptional.isPresent()){
            return "redirect:/error";
        }
        Author authorObj=authorOptional.get();
        PatentDocument patentDocument = patentDocumentManagerInterface.findById(id).orElseThrow();
        patentDocument.setName(name);
        patentDocument.setAuthor(authorObj);
        patentDocument.setLocation(locationObj);
        patentDocument.setPatentNumber(patentNumber);
        patentDocument.setAddedDate(addDate);
        patentDocument.setModificationDate(modDate);
        patentDocumentBusinessLogicInterface.editPatDoc(patentDocument);
        log.info("PatentDocument updated ");
        return "redirect:/patentDoc-done";
    }
    @GetMapping("/patentDoc-done")
    public String patentDocDone(Model model){
        return "patentDoc/patentDoc-done";
    }

    @PreAuthorize("hasAuthority('write')")
    @PostMapping("/patentDoc/{id}/remove")
    public String patentDocDelete(@PathVariable(value="id") Long id, Model model) {
        PatentDocument patentDocument = patentDocumentManagerInterface.findById(id).orElseThrow();
        patentDocumentBusinessLogicInterface.deletePatDoc(patentDocument);
        log.info("PatentDocument deleted ");
        return "redirect:/patentDoc-delete";
    }
    @GetMapping("/patentDoc-delete")
    public String patentDocDeleted(Model model){
        return "patentDoc/patentDoc-delete";
    }

    @GetMapping("patentDoc/parametrSearch")
    public String patDocParametrSearchPage(Model model){
        return "patentDoc/patentDoc-parametrSearch";
    }
    @PostMapping("patentDoc/parametrSearch")
    public String patDocParametrSearch(@RequestParam String name, @RequestParam String author,
                                     @RequestParam String location, @RequestParam String patentNumber, @RequestParam String modificationDate,
                                     @RequestParam String addedDate, Model model) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date modDate;
        Date addDate;
        if(patentNumber.equals("")) patentNumber=null;

        if(!modificationDate.equals("")){
            modDate= format.parse(modificationDate);
        }
        else modDate=null;
        if(!addedDate.equals("")){
            addDate= format.parse(addedDate);
        }
        else addDate=null;
        Location locationObj=locationManagerInterface.getByNameORNULL(location);
        Author authorObj=authorManagerInterface.getByNameORNULL(author);
        Iterable<PatentDocument> patDoc = patentDocumentManagerInterface.parametrSearch(name,authorObj,locationObj,patentNumber,modDate,addDate);
        patentDocumentBusinessLogicInterface.searchPatDoc(patDoc,model);
        return "patentDoc/patentDoc-search";
    }
}
