package testingforfun.controllers;

import testingforfun.models.Documents;
import testingforfun.repo.DocumentRepository;
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
public class DocumentController {
    @Autowired
    DocumentRepository documentRepository;
    @GetMapping("/document")
    public String allDocs( Model model) {
        Iterable<Documents> docs= documentRepository.findAll();
        model.addAttribute("docs",docs);
        return "document/doc-main";
    }
    @GetMapping("/document/{id}")
    public String docDetails(@PathVariable(value="id") Long id, Model model) {
        if(!documentRepository.existsById(id)){
            return "redirect:/error";
        }
        Optional<Documents> doc= documentRepository.findById(id);
        ArrayList<Documents> res= new ArrayList<>();
        doc.ifPresent(res::add);
        model.addAttribute("doc",res);
        return "document/doc-details";
    }
    @GetMapping("/document/add")
    public String addDocPage( Model model) {
        return "document/doc-add";
    }
    @PostMapping("/document/add")
    public String addDoc(@RequestParam String name, @RequestParam String documentNumber, @RequestParam String location,
                          @RequestParam String creationDate, @RequestParam String modificationDate,
                          @RequestParam String addedDate, Model model) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date creatDate= format.parse(creationDate);
        Date addDate= format.parse(addedDate);
        Date modDate= format.parse(modificationDate);
        Documents document = new Documents(name,documentNumber,location,creatDate,addDate,modDate);
        documentRepository.save(document);
        return "document/doc-done";
    }
    @GetMapping("/document/{id}/edit")
    public String docEdit(@PathVariable(value="id") Long id, Model model) {
        if(!documentRepository.existsById(id)){
            return "redirect:/error";
        }
        Optional<Documents> document= documentRepository.findById(id);
        ArrayList<Documents> res= new ArrayList<>();
        document.ifPresent(res::add);
        model.addAttribute("doc",res);
        return "document/doc-edit";
    }
    @GetMapping("/doc-done")
    public String docDone( Model model) {
        return "document/doc-done";
    }
    @PostMapping("/document/{id}/edit")
    public String blogPostEdit(@PathVariable(value="id") Long id,@RequestParam String name, @RequestParam String documentNumber, @RequestParam String location,
                               @RequestParam String creationDate, @RequestParam String modificationDate,
                               @RequestParam String addedDate, Model model) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date creatDate= format.parse(creationDate);
        Date addDate= format.parse(addedDate);
        Date modDate= format.parse(modificationDate);
        Documents document = documentRepository.findById(id).orElseThrow();
        document.setName(name);
        document.setDocumentNumber(documentNumber);
        document.setLocation(location);
        document.setCreationDate(creatDate);
        document.setAddedDate(addDate);
        document.setModificationDate(modDate);
        documentRepository.save(document);
        return "redirect:/doc-done";
    }
    @PostMapping("/document/{id}/remove")
    public String bookDelete(@PathVariable(value="id") Long id, Model model) {
        Documents document = documentRepository.findById(id).orElseThrow();
        documentRepository.delete(document);
        return "redirect:/doc-delete";
    }
    @GetMapping("/doc-delete")
    public String bookDeleted(Model model){
        return "document/doc-delete";
    }
}
