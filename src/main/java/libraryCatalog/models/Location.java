package libraryCatalog.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
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

    @OneToMany (mappedBy="location",fetch = FetchType.EAGER)
    private List<Book> books;


    Location(){

    }

    public Location(String name, String libraryNumber) {
        this.name = name;
        this.libraryNumber = libraryNumber;
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

    @Override
    public String toString() {
        return
                "Name:" + name +
                ", Library Number:" + libraryNumber ;
    }
}
