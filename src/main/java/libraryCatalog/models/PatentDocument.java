package libraryCatalog.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PatentDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String patentNumber;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String author;

    @ManyToOne (optional=false, cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn (name="locationid")
    private Location patentDocLocation;

    @Temporal(TemporalType.DATE)
    private Date addedDate;

    @Temporal(TemporalType.DATE)
    private Date modificationDate;
    PatentDocument(){

    }

    public PatentDocument(String name, String patentNumber, String author, Location location, Date addedDate, Date modificationDate) {
        this.name = name;
        this.patentNumber = patentNumber;
        this.author = author;
        this.patentDocLocation = location;
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

    public String getPatentNumber() {
        return patentNumber;
    }

    public void setPatentNumber(String patentNumber) {
        this.patentNumber = patentNumber;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Location getLocation() {
        return patentDocLocation;
    }

    public void setLocation(Location location) {
        this.patentDocLocation = location;
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
        return "PatentDocument{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", patentNumber='" + patentNumber + '\'' +
                ", author='" + author + '\'' +
                ", location='" + patentDocLocation + '\'' +
                ", addedDate=" + addedDate +
                ", modificationDate=" + modificationDate +
                '}';
    }
}
