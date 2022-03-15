package libraryCatalog.controllers;

import libraryCatalog.models.Books;
import libraryCatalog.models.Documents;
import libraryCatalog.models.PatentDocuments;
import libraryCatalog.repo.PatentDocumentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Controller
public class PatentDocumentController {
    @Autowired
    PatentDocumentsRepository patentDocumentsRepository;
    @GetMapping("/patentDoc")
    public String allPatentDoc( Model model) {
        Iterable<PatentDocuments> patDocs= patentDocumentsRepository.findAll();
        model.addAttribute("patDocs",patDocs);
        return "patentDoc/patentDoc-main";
    }
    @GetMapping("/patentDoc/{id}")
    public String patentDocDetails(@PathVariable(value="id") Long id, Model model) {
        if(!patentDocumentsRepository.existsById(id)){
            return "redirect:/error";
        }
        Optional<PatentDocuments> patentDocument= patentDocumentsRepository.findById(id);
        ArrayList<PatentDocuments> res= new ArrayList<>();
        patentDocument.ifPresent(res::add);
        model.addAttribute("patDoc",res);
        return "patentDoc/patentDoc-details";
    }
    @GetMapping("/patentDoc/add")
    public String addPatentDocPage( Model model) {
        return "patentDoc/patentDoc-add";
    }
    @PostMapping("/patentDoc/add")
    public String addPatentDoc(@RequestParam String name, @RequestParam String author, @RequestParam String location,
                               @RequestParam String patentNumber,
                               @RequestParam String modificationDate,
                               @RequestParam String addedDate, Model model) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date addDate= format.parse(addedDate);
        Date modDate= format.parse(modificationDate);
        PatentDocuments patentDocument = new PatentDocuments(name,patentNumber,author,location,addDate,modDate);
        patentDocumentsRepository.save(patentDocument);
        return "patentDoc/patentDoc-done";
    }
    @GetMapping("/patentDoc/{id}/edit")
    public String patentDocEditPage(@PathVariable(value="id") Long id, Model model) {
        if(!patentDocumentsRepository.existsById(id)){
            return "redirect:/error";
        }
        Optional<PatentDocuments> patentDocuments= patentDocumentsRepository.findById(id);
        ArrayList<PatentDocuments> res= new ArrayList<>();
        patentDocuments.ifPresent(res::add);
        model.addAttribute("patDoc",res);
        return "patentDoc/patentDoc-edit";
    }
    @PostMapping("/patentDoc/{id}/edit")
    public String patentDocEdit(@PathVariable(value="id") Long id,@RequestParam String name, @RequestParam String author,
                               @RequestParam String location,@RequestParam String patentNumber,
                               @RequestParam String modificationDate, @RequestParam String addedDate, Model model) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date addDate= format.parse(addedDate);
        Date modDate= format.parse(modificationDate);
        PatentDocuments patentDocument = patentDocumentsRepository.findById(id).orElseThrow();
        patentDocument.setName(name);
        patentDocument.setAuthor(author);
        patentDocument.setLocation(location);
        patentDocument.setPatentNumber(patentNumber);
        patentDocument.setAddedDate(addDate);
        patentDocument.setModificationDate(modDate);
        patentDocumentsRepository.save(patentDocument);
        return "redirect:/patentDoc-done";
    }
    @GetMapping("/patentDoc-done")
    public String patentDocDone(Model model){
        return "patentDoc/patentDoc-done";
    }
    @PostMapping("/patentDoc/{id}/remove")
    public String patentDocDelete(@PathVariable(value="id") Long id, Model model) {
        PatentDocuments patentDocument = patentDocumentsRepository.findById(id).orElseThrow();
        patentDocumentsRepository.delete(patentDocument);
        return "redirect:/patentDoc-delete";
    }
    @GetMapping("/patentDoc-delete")
    public String patentDocDeleted(Model model){
        return "patentDoc/patentDoc-delete";
    }
    @PostMapping("/patentDoc/search")
    public String patentDocSearch(@RequestParam String name, Model model){
        Iterable<PatentDocuments> documents= patentDocumentsRepository.getByName(name);
        model.addAttribute("patDoc",documents);
        return "patentDoc/patentDoc-search";
    }
}
