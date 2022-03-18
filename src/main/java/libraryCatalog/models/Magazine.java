package libraryCatalog.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
@Entity
public class Magazine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String name;

    @ManyToOne (optional=false, cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn (name="locationid")
    private Location magazineLocation;

    @Temporal(TemporalType.DATE)
    private Date publicationDate;


    @Temporal(TemporalType.DATE)
    private Date addedDate;

    @Temporal(TemporalType.DATE)
    private Date modificationDate;
    Magazine(){

    }

    public Magazine(String name, Location location, Date publicationDate, Date addedDate, Date modificationDate) {
        this.name = name;
        this.magazineLocation = location;
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

    public Location getLocation() {
        return magazineLocation;
    }

    public void setLocation(Location location) {
        this.magazineLocation = location;
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
        return "Magazine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + magazineLocation + '\'' +
                ", publicationDate=" + publicationDate +
                ", addedDate=" + addedDate +
                ", modificationDate=" + modificationDate +
                '}';
    }
}
