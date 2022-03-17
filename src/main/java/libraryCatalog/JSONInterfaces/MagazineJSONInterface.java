package libraryCatalog.JSONInterfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import libraryCatalog.models.Magazine;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface MagazineJSONInterface {
    public void MagazineToJSONandFile(Magazine magazine) throws IOException;
    public void DeleteJSON(Magazine magazine);
    public Magazine getMagazineJSONFromFile(Long id) throws FileNotFoundException, JsonProcessingException;
}
