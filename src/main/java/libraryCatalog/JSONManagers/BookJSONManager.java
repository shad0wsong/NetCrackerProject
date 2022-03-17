package libraryCatalog.JSONManagers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import libraryCatalog.JSONInterfaces.BookJSONInterface;
import libraryCatalog.JSONInterfaces.DocJSONInterface;
import libraryCatalog.models.Book;
import libraryCatalog.models.Document;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Scanner;

@Component
public class BookJSONManager implements BookJSONInterface {

    public void BookToJSONandFile(Book book) throws IOException {
        FileWriter writer = new FileWriter("src/main/resources/BookJSONFormat/"+book.getId()+".json", false);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonFormat=objectMapper.writeValueAsString(book);
        writer.write(jsonFormat);
        writer.flush();
        writer.close();
    }

    public void DeleteJSON(Book book) {
        File f= new File("src/main/resources/BookJSONFormat/"+book.getId()+".json");
        f.delete();
    }
    public Book getDocJSONFromFile(Long id) throws FileNotFoundException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        FileReader fileReader = new FileReader("src/main/resources/BookJSONFormat/"+id+".json");
        Scanner scanner = new Scanner(fileReader);
        String doc_str=scanner.nextLine();
        Book book = objectMapper.readValue(doc_str,Book.class);
        return  book;
    }
}
