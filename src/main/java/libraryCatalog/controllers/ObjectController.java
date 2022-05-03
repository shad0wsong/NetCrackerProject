package libraryCatalog.controllers;


import libraryCatalog.businessLogicInterfaces.ObjectBusinessLogicInterface;
import libraryCatalog.models.*;
import libraryCatalog.models.Object;
import libraryCatalog.repoInterfaces.*;
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
public class ObjectController {
    static Logger log = Logger.getLogger(ObjectController.class.getName());
    @Autowired
    ObjectManagerInterface objectManagerInterface;

    @Autowired
    ObjectBusinessLogicInterface objectBusinessLogicInterface;

    @Autowired
    ObjectTypeManagerInterface objectTypeManagerInterface;

    @GetMapping("/object")
    public String allObject( Model model) {
        Iterable<Object> objects= objectManagerInterface.findAll();
        objectBusinessLogicInterface.getAllObject(objects,model);
        return "object/object-main";
    }
    @GetMapping("/object/{id}")
    public String objectDetails(@PathVariable(value="id") Long id, Model model) {
        if(!objectBusinessLogicInterface.objectExistByID(id)){
            log.error("Object not found ");
            return "redirect:/error";
        }
        Optional<Object> object= objectManagerInterface.findById(id);
        objectBusinessLogicInterface.getObjectDetails(object,model);
        return "object/object-details";
    }
    @PreAuthorize("hasAuthority('write')")
    @GetMapping("/object/add")
    public String addObjectPage( Model model) {
        return "object/object-add";
    }

    @PreAuthorize("hasAuthority('write')")
    @PostMapping("/object/add")
    public String addObject(@RequestParam String name, @RequestParam String objectType, Model model) throws ParseException, IOException {
        ObjectType objectT=objectTypeManagerInterface.getByName(objectType);
        Object object = new Object(objectT,name);
        objectBusinessLogicInterface.createObject(object);
        log.info("Object added");
        return "object/object-done";
    }

    @PreAuthorize("hasAuthority('write')")
    @GetMapping("/object/{id}/edit")
    public String objectEditPage(@PathVariable(value="id") Long id, Model model) {
        if(!objectBusinessLogicInterface.objectExistByID(id)){
            log.error("Object not found ");
            return "redirect:/error";
        }
        Optional<Object> object= objectManagerInterface.findById(id);
        objectBusinessLogicInterface.getObjectDetails(object,model);
        return "object/object-edit";
    }

    @PreAuthorize("hasAuthority('write')")
    @PostMapping("/object/{id}/edit")
    public String objectEdit(@PathVariable(value="id") Long id, @RequestParam String name,@RequestParam String objectType,@RequestParam Long valueid, Model model) throws ParseException, IOException {
        ObjectType objectT=objectTypeManagerInterface.getByName(objectType);
        Object object = objectManagerInterface.findById(id).orElseThrow();
        object.setName(name);
        object.setObjectType(objectT);
        objectBusinessLogicInterface.editObject(object,model);
        log.info("Object updated ");
        return "redirect:/object-done";
    }
    @GetMapping("/object-done")
    public String objectDone(Model model){
        return "object/object-done";
    }

    @PreAuthorize("hasAuthority('write')")
    @PostMapping("/object/{id}/remove")
    public String objectDelete(@PathVariable(value="id") Long id, Model model) {
        Object object = objectManagerInterface.findById(id).orElseThrow();
        objectBusinessLogicInterface.deleteObject(object);
        log.info("Object deleted");
        return "redirect:/object-delete";
    }
    @GetMapping("/object-delete")
    public String objectDeleted(Model model){
        return "object/object-delete";
    }

}
