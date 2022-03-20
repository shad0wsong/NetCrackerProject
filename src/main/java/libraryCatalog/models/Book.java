package libraryCatalog.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@jsonID")
public class Book {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
     private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String ISBN;


    @ManyToOne (optional=false, cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn (name="authorid")

    private Author bookAuthor;

    @ManyToOne (optional=false, cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn (name="locationid")

    private Location location;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    private Date publicationDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    private Date addedDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    private Date modificationDate;
    Book(){

    }

    public Book(String name, String ISBN, Author author, Location location, Date publicationDate, Date addedDate, Date modificationDate) {
        this.name = name;
        this.ISBN = ISBN;
        this.bookAuthor = author;
        this.location = location;
        this.publicationDate = publicationDate;
        this.addedDate = addedDate;
        this.modificationDate = modificationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public Author getAuthor() {
        return bookAuthor;
    }

    public void setAuthor(Author author) {
        this.bookAuthor = author;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", author='" + bookAuthor + '\'' +
                ", location='" + location + '\'' +
                ", publicationDate=" + publicationDate +
                ", addedDate=" + addedDate +
                ", modificationDate=" + modificationDate +
                '}';
    }
}
