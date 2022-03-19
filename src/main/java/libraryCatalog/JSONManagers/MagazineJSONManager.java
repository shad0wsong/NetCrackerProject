package libraryCatalog.JSONManagers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import libraryCatalog.JSONInterfaces.DocJSONInterface;
import libraryCatalog.JSONInterfaces.MagazineJSONInterface;
import libraryCatalog.models.Document;
import libraryCatalog.models.Magazine;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Scanner;

@Component
public class MagazineJSONManager implements MagazineJSONInterface {

    public void MagazineToJSONandFile(Magazine magazine) throws IOException {
        FileWriter writer = new FileWriter("src/main/resources/MagazineJSONFormat/"+magazine.getId()+".json", false);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        String jsonFormat=objectMapper.writeValueAsString(magazine);
        writer.write(jsonFormat);
        writer.flush();
        writer.close();
    }

    public void DeleteJSON(Magazine magazine) {
        File f= new File("src/main/resources/MagazineJSONFormat/"+magazine.getId()+".json");
        f.delete();
    }
    public Magazine getMagazineJSONFromFile(Long id) throws FileNotFoundException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        FileReader fileReader = new FileReader("src/main/resources/MagazineJSONFormat/"+id+".json");
        Scanner scanner = new Scanner(fileReader);
        String doc_str=scanner.nextLine();
        Magazine magazine = objectMapper.readValue(doc_str,Magazine.class);
        return  magazine;
    }
}
