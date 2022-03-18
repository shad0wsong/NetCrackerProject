package libraryCatalog.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@jsonID")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long authorid;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
     private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
     private String shortBiography;

    @OneToMany (mappedBy="patentDocAuthor")
    private List<PatentDocument> patentDocuments;

    @OneToMany (mappedBy="bookAuthor")
    @JsonManagedReference
    private List<Book> books;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortBiography() {
        return shortBiography;
    }

    public void setShortBiography(String shortBiography) {
        this.shortBiography = shortBiography;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return  name;

    }
}
