package libraryCatalog.JSONManagers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import libraryCatalog.JSONInterfaces.DocJSONInterface;
import libraryCatalog.models.Document;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Scanner;

@Component
public class DocJSONManager implements DocJSONInterface {

    public void DocToJSONandFile(Document document) throws IOException {
        FileWriter writer = new FileWriter("src/main/resources/DocJSONFormat/"+document.getId()+".json", false);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        String jsonFormat=objectMapper.writeValueAsString(document);
        writer.write(jsonFormat);
        writer.flush();
        writer.close();
    }

    public void DeleteJSON(Document document) {
        File f= new File("src/main/resources/DocJSONFormat/"+document.getId()+".json");
        f.delete();
    }
    public Document getDocJSONFromFile(Long id) throws FileNotFoundException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        FileReader fileReader = new FileReader("src/main/resources/DocJSONFormat/"+id+".json");
        Scanner scanner = new Scanner(fileReader);
        String doc_str=scanner.nextLine();
        Document document = objectMapper.readValue(doc_str,Document.class);
        return  document;
    }
}
