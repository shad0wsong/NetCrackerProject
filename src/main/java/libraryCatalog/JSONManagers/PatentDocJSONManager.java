package libraryCatalog.JSONManagers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import libraryCatalog.JSONInterfaces.BookJSONInterface;
import libraryCatalog.JSONInterfaces.PatentDocJSONInterface;
import libraryCatalog.models.Book;
import libraryCatalog.models.PatentDocument;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Scanner;

@Component
public class PatentDocJSONManager implements PatentDocJSONInterface {

    public void PatentDocToJSONandFile(PatentDocument patentDocument) throws IOException {
        FileWriter writer = new FileWriter("src/main/resources/PatentDocJSONFormat/"+patentDocument.getId()+".json", false);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        String jsonFormat=objectMapper.writeValueAsString(patentDocument);
        writer.write(jsonFormat);
        writer.flush();
        writer.close();
    }

    public void DeleteJSON(PatentDocument patentDocument) {
        File f= new File("src/main/resources/PatentDocJSONFormat/"+patentDocument.getId()+".json");
        f.delete();
    }
    public PatentDocument getPatentDocJSONFromFile(Long id) throws FileNotFoundException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        FileReader fileReader = new FileReader("src/main/resources/PatentDocJSONFormat/"+id+".json");
        Scanner scanner = new Scanner(fileReader);
        String doc_str=scanner.nextLine();
        PatentDocument patentDocument = objectMapper.readValue(doc_str,PatentDocument.class);
        return  patentDocument;
    }
}
