package libraryCatalog.controllers;

import libraryCatalog.businessLogicInterfaces.LocationBusinessLogicInterface;
import libraryCatalog.models.Author;
import libraryCatalog.models.Book;
import libraryCatalog.models.Location;
import libraryCatalog.repoInterfaces.LocationManagerInterface;
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
public class LocationController {
    static Logger log = Logger.getLogger(LocationController.class.getName());
    @Autowired
    LocationManagerInterface locationManagerInterface;
    @Autowired
    LocationBusinessLogicInterface locationBusinessLogicInterface;
    @GetMapping("/location")
    public String allLocation( Model model) {
        Iterable<Location> locations= locationManagerInterface.findAll();
        locationBusinessLogicInterface.getAllLocation(locations,model);
        return "location/location-main";
    }
    @GetMapping("/location/{id}")
    public String locationDetails(@PathVariable(value="id") Long id, Model model) {
        if(!locationBusinessLogicInterface.locationExistByID(id)){
            log.error("Location not found ");
            return "redirect:/error";
        }
        Optional<Location> location= locationManagerInterface.findById(id);
        locationBusinessLogicInterface.getLocationDetails(location,model);
        return "location/location-details";
    }

    @PreAuthorize("hasAuthority('write')")
    @GetMapping("/location/add")
    public String addLocationPage( Model model) {
        return "location/location-add";
    }

    @PreAuthorize("hasAuthority('write')")
    @PostMapping("/location/add")
    public String addLocation(@RequestParam String name, @RequestParam String libraryNumber, Model model)  {
        Location location = new Location(name, libraryNumber);
        locationBusinessLogicInterface.createLocation(location);
        log.info("Location added");
        return "location/location-done";
    }

    @PreAuthorize("hasAuthority('write')")
    @GetMapping("/location/{id}/edit")
    public String locationEditPage(@PathVariable(value="id") Long id, Model model) {
        if(!locationBusinessLogicInterface.locationExistByID(id)){
            log.error("Location not found ");
            return "redirect:/error";
        }
        Optional<Location> location= locationManagerInterface.findById(id);
        locationBusinessLogicInterface.getLocationDetails(location,model);
        return "location/location-edit";
    }

    @PreAuthorize("hasAuthority('write')")
    @PostMapping("/location/{id}/edit")
    public String locationEdit(@PathVariable(value="id") Long id, @RequestParam String name, @RequestParam String libraryNumber,
                            Model model)  {
        Location location = locationManagerInterface.findById(id).orElseThrow();
        location.setName(name);
        location.setLibraryNumber(libraryNumber);
        locationBusinessLogicInterface.createLocation(location);
        log.info("Location updated ");
        return "redirect:/location-done";
    }
    @GetMapping("/location-done")
    public String locationDone(Model model){
        return "location/location-done";
    }

    @PreAuthorize("hasAuthority('write')")
    @PostMapping("/location/{id}/remove")
    public String locationDelete(@PathVariable(value="id") Long id, Model model) {
        Location location = locationManagerInterface.findById(id).orElseThrow();
        locationBusinessLogicInterface.deleteLocation(location);
        log.info("Location deleted");
        return "redirect:/location-delete";
    }
    @GetMapping("/location-delete")
    public String locationDeleted(Model model){
        return "location/location-delete";
    }


}
