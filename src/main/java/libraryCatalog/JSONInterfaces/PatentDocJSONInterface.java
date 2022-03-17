package libraryCatalog.JSONInterfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import libraryCatalog.models.PatentDocument;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface PatentDocJSONInterface {
    void PatentDocToJSONandFile(PatentDocument patentDocument) throws IOException;
    void DeleteJSON(PatentDocument patentDocument);
    PatentDocument getPatentDocJSONFromFile(Long id) throws FileNotFoundException, JsonProcessingException;
}
