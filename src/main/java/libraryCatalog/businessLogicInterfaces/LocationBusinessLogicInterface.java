package libraryCatalog.businessLogicInterfaces;

import libraryCatalog.models.Location;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.Optional;

public interface LocationBusinessLogicInterface {
     void createLocation(Location location) ;
     void getAllLocation(Iterable<Location> locations, Model model);
     void getLocationDetails(Optional<Location> location , Model model);
     void deleteLocation(Location location);
     boolean locationExistByID(Long id);
}
