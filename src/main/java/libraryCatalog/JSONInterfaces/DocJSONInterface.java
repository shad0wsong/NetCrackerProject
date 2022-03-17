package libraryCatalog.JSONInterfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import libraryCatalog.models.Document;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface DocJSONInterface {
     void DocToJSONandFile(Document document) throws IOException;
     void DeleteJSON(Document document);
     Document getDocJSONFromFile(Long id) throws FileNotFoundException, JsonProcessingException;
}
