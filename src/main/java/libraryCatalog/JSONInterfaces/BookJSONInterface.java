package libraryCatalog.JSONInterfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import libraryCatalog.models.Book;
import libraryCatalog.models.Document;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface BookJSONInterface {
    void BookToJSONandFile(Book book) throws IOException;
    void DeleteJSON(Book book);
    Book getDocJSONFromFile(Long id) throws FileNotFoundException, JsonProcessingException;
}
