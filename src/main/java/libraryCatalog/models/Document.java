package libraryCatalog.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@jsonID")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String documentNumber;

    @ManyToOne (optional=false, cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn (name="locationid")
    private Location docLocation;

    @Temporal(TemporalType.DATE)
    private Date creationDate;


    @Temporal(TemporalType.DATE)
    private Date addedDate;

    @Temporal(TemporalType.DATE)
    private Date modificationDate;

    Document(){

    }

    public Document(String name, String documentNumber, Location location, Date creationDate, Date addedDate, Date modificationDate) {
        this.name = name;
        this.documentNumber = documentNumber;
        this.docLocation = location;
        this.creationDate = creationDate;
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

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Location getLocation() {
        return docLocation;
    }

    public void setLocation(Location location) {
        this.docLocation = location;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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
        return "Document{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", documentNumber='" + documentNumber + '\'' +
                ", location='" + docLocation + '\'' +
                ", creationDate=" + creationDate +
                ", addedDate=" + addedDate +
                ", modificationDate=" + modificationDate +
                '}';
    }
}
