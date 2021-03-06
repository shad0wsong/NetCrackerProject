package libraryCatalog.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@jsonID")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long locationid;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String libraryNumber;

    @OneToMany (mappedBy="location")

    private List<Book> books;

    @OneToMany (mappedBy="docLocation")

    private List<Document> documents;

    @OneToMany (mappedBy="patentDocLocation")

    private List<PatentDocument> patentDocuments;

    @OneToMany (mappedBy="magazineLocation")

    private List<Magazine> magazines;


    public Location(String name, String libraryNumber) {
        this.name = name;
        this.libraryNumber = libraryNumber;
    }
    public  Location(){

    }

    public Long getId() {
        return locationid;
    }

    public void setId(Long id) {
        this.locationid = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLibraryNumber() {
        return libraryNumber;
    }

    public void setLibraryNumber(String libraryNumber) {
        this.libraryNumber = libraryNumber;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public List<PatentDocument> getPatentDocuments() {
        return patentDocuments;
    }

    public void setPatentDocuments(List<PatentDocument> patentDocuments) {
        this.patentDocuments = patentDocuments;
    }

    public List<Magazine> getMagazines() {
        return magazines;
    }

    public void setMagazines(List<Magazine> magazines) {
        this.magazines = magazines;
    }

    @Override
    public String toString() {
        return name;
    }
}
