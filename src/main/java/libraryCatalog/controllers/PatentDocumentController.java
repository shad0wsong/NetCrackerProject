package libraryCatalog.controllers;

import libraryCatalog.businessLogic.PatentDocumentBusinessLogic;
import libraryCatalog.models.PatentDocument;
import libraryCatalog.repo.PatentDocumentRepository;
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
    PatentDocumentBusinessLogic patentDocumentBusinessLogic;
    @GetMapping("/patentDoc")
    public String allPatentDoc( Model model) {
        patentDocumentBusinessLogic.getAllPatDoc(model);
        return "patentDoc/patentDoc-main";
    }
    @GetMapping("/patentDoc/{id}")
    public String patentDocDetails(@PathVariable(value="id") Long id, Model model) {
        if(!patentDocumentBusinessLogic.patDocExistByID(id)){
            return "redirect:/error";
        }
        patentDocumentBusinessLogic.getPatDocDetails(id, model);
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
        patentDocumentBusinessLogic.createPatDoc(name,patentNumber,author,location,addDate,modDate);
        return "patentDoc/patentDoc-done";
    }
    @GetMapping("/patentDoc/{id}/edit")
    public String patentDocEditPage(@PathVariable(value="id") Long id, Model model) {
        if(!patentDocumentBusinessLogic.patDocExistByID(id)){
            return "redirect:/error";
        }
        patentDocumentBusinessLogic.getPatDocDetails(id, model);
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
        patentDocumentBusinessLogic.editPatDoc(id,name,patentNumber,author,location,addDate,modDate);
        return "redirect:/patentDoc-done";
    }
    @GetMapping("/patentDoc-done")
    public String patentDocDone(Model model){
        return "patentDoc/patentDoc-done";
    }
    @PostMapping("/patentDoc/{id}/remove")
    public String patentDocDelete(@PathVariable(value="id") Long id, Model model) {
        patentDocumentBusinessLogic.deletePatDoc(id);
        return "redirect:/patentDoc-delete";
    }
    @GetMapping("/patentDoc-delete")
    public String patentDocDeleted(Model model){
        return "patentDoc/patentDoc-delete";
    }
    @PostMapping("/patentDoc/search")
    public String patentDocSearch(@RequestParam String name, Model model){
        patentDocumentBusinessLogic.searchPatDocByName(name,model);
        return "patentDoc/patentDoc-search";
    }
}
