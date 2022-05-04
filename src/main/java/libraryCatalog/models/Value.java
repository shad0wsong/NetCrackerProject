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


    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "attrebutid")
    private Attrebutes attrebute;


    @ManyToOne(optional = false, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "objectid")
    private Object object;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String stringValue;


    private Long longValue;


    private Long authorid;


    private Long locationid;


    @Temporal(TemporalType.DATE)
    private Date dateValue;


    public Value() {
    }

    public Value(Attrebutes attrebute, Object object, String stringValue, Long longValue, Long authorid, Long locationid, Date dateValue) {
        this.attrebute = attrebute;
        this.object = object;
        this.stringValue = stringValue;
        this.longValue = longValue;
        this.authorid = authorid;
        this.locationid = locationid;
        this.dateValue = dateValue;
    }

    public Long getValueid() {
        return valueid;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Long getLongValue() {
        return longValue;
    }

    public void setLongValue(Long intValue) {
        this.longValue = intValue;
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

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }

    public Long getId() {
        return valueid;
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

    @Override
    public String toString() {
        return "Value{" +
                "valueid=" + valueid +
                ", attrebute=" + attrebute +
                ", object=" + object +
                ", stringValue='" + stringValue + '\'' +
                ", longValue=" + longValue +
                ", authorid=" + authorid +
                ", locationid=" + locationid +
                ", dateValue=" + dateValue +
                '}';
    }
}
