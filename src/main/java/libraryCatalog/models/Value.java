package libraryCatalog.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(indexes = @Index(columnList = "valueid"))
public class Value {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long valueid;


    @OneToOne(optional = false, mappedBy = "value")
    private Attrebutes attrebute;

    @OneToOne(optional = false, mappedBy = "value")
    private Object object;


    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String ISBN;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String documentNumber;


    private Long authorid;


    private Long locationid;


    @Temporal(TemporalType.DATE)
    private Date publicationDate;


    @Temporal(TemporalType.DATE)
    private Date addedDate;


    @Temporal(TemporalType.DATE)
    private Date modificationDate;


    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String patentNumber;


    public Value() {
    }

    public Long getId() {
        return valueid;
    }

    public Value(Attrebutes attrebute, Object object, String name, String ISBN, String documentNumber, Long authorid, Long locationid, Date publicationDate, Date addedDate, Date modificationDate, Date creationDate, String patentNumber) {
        this.attrebute = attrebute;
        this.object = object;
        this.name = name;
        this.ISBN = ISBN;
        this.documentNumber = documentNumber;
        this.authorid = authorid;
        this.locationid = locationid;
        this.publicationDate = publicationDate;
        this.addedDate = addedDate;
        this.modificationDate = modificationDate;
        this.creationDate = creationDate;
        this.patentNumber = patentNumber;
    }

    public void setValueid(Long valueid) {
        this.valueid = valueid;
    }


    public Attrebutes getAttrebute() {
        return attrebute;
    }

    public void setAttrebute(Attrebutes attrebute) {
        this.attrebute = attrebute;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
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

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Long getAuthorid() {
        return authorid;
    }

    public void setAuthorid(Long authorid) {
        this.authorid = authorid;
    }

    public Long getLocationid() {
        return locationid;
    }

    public void setLocationid(Long locationid) {
        this.locationid = locationid;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getPatentNumber() {
        return patentNumber;
    }

    public void setPatentNumber(String patentNumber) {
        this.patentNumber = patentNumber;
    }


    @Override
    public String toString() {
        return
                "valueid=" + valueid +
                ", name='" + name + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", documentNumber='" + documentNumber + '\'' +
                ", authorid=" + authorid +
                ", locationid=" + locationid +
                ", publicationDate=" + publicationDate +
                ", addedDate=" + addedDate +
                ", modificationDate=" + modificationDate +
                ", creationDate=" + creationDate +
                ", patentNumber='" + patentNumber;
    }
}
