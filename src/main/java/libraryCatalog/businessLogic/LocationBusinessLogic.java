package libraryCatalog.businessLogic;

import libraryCatalog.JSONInterfaces.BookJSONInterface;
import libraryCatalog.businessLogicInterfaces.LocationBusinessLogicInterface;
import libraryCatalog.models.Book;
import libraryCatalog.models.Location;
import libraryCatalog.repoInterfaces.BookManagerInterface;
import libraryCatalog.repoInterfaces.LocationManagerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class LocationBusinessLogic implements LocationBusinessLogicInterface {
    @Autowired
    LocationManagerInterface locationManagerInterface;
    public void createLocation(Location location) {
        locationManagerInterface.save(location);
    }
    public void getAllLocation(Iterable<Location> locations, Model model){
        model.addAttribute("locations",locations);
    }
    public void getLocationDetails(Optional<Location> location , Model model){
        ArrayList<Location> res= new ArrayList<>();
        location.ifPresent(res::add);
        model.addAttribute("location",res);
    }
    public void deleteLocation(Location location){
        locationManagerInterface.delete(location);

    }
    public boolean locationExistByID(Long id){
        if(locationManagerInterface.existsById(id)){
            return true ;
        }
        return false;
    }

}
