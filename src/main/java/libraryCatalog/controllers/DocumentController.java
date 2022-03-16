package libraryCatalog.controllers;

import libraryCatalog.businessLogic.DocumentBusinessLogic;
import libraryCatalog.models.Document;
import libraryCatalog.repo.DocumentRepository;
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
    DocumentBusinessLogic documentBusinessLogic;

    @GetMapping("/document")
    public String allDocs( Model model) {
        documentBusinessLogic.getAllDocs(model);
        return "document/doc-main";
    }
    @GetMapping("/document/{id}")
    public String docDetails(@PathVariable(value="id") Long id, Model model) {
        if(!documentBusinessLogic.docExistByID(id)){
            return "redirect:/error";
        }
        documentBusinessLogic.getDocDetails(id, model);
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
        documentBusinessLogic.createDoc(name,documentNumber,location,creatDate,addDate,modDate);
        return "document/doc-done";
    }
    @GetMapping("/document/{id}/edit")
    public String docEditPage(@PathVariable(value="id") Long id, Model model) {
        if(!documentBusinessLogic.docExistByID(id)){
            return "redirect:/error";
        }
        documentBusinessLogic.getDocDetails(id, model);
        return "document/doc-edit";
    }
    @GetMapping("/doc-done")
    public String docDone( Model model) {
        return "document/doc-done";
    }
    @PostMapping("/document/{id}/edit")
    public String DocEdit(@PathVariable(value="id") Long id,@RequestParam String name, @RequestParam String documentNumber, @RequestParam String location,
                               @RequestParam String creationDate, @RequestParam String modificationDate,
                               @RequestParam String addedDate, Model model) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date creatDate= format.parse(creationDate);
        Date addDate= format.parse(addedDate);
        Date modDate= format.parse(modificationDate);
        documentBusinessLogic.editDoc(id,name,documentNumber,location,creatDate,addDate,modDate);
        return "redirect:/doc-done";
    }
    @PostMapping("/document/{id}/remove")
    public String docDelete(@PathVariable(value="id") Long id, Model model) {
        documentBusinessLogic.deleteDoc(id);
        return "redirect:/doc-delete";
    }
    @GetMapping("/doc-delete")
    public String docDeleted(Model model){
        return "document/doc-delete";
    }

    @PostMapping("/document/search")
    public String docSearch(@RequestParam String name, Model model){
        documentBusinessLogic.searchDocByName(name, model);
        return "document/doc-search";
    }
}
