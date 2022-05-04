package libraryCatalog.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Attrebutes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long attrebutid;

    @OneToOne(optional = false, mappedBy = "attrebute")
    private Value value;


    @ManyToMany(mappedBy = "attrebutes")
    private Collection<ObjectType> objectTypeCollection;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String type;


    public Attrebutes() {
    }

    public Attrebutes(Value value, Collection<ObjectType> objectTypeCollection, String name, String type) {
        this.value = value;
        this.objectTypeCollection = objectTypeCollection;
        this.name = name;
        this.type = type;
    }

    public Long getId() {
        return attrebutid;
    }

    public void setAttrebutid(Long attrebutid) {
        this.attrebutid = attrebutid;
    }

    public Collection<ObjectType> getObjectTypeCollection() {
        return objectTypeCollection;
    }

    public void setObjectTypeCollection(Collection<ObjectType> objectTypeCollection) {
        this.objectTypeCollection = objectTypeCollection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return
                "name='" + name +
                ", type='" + type;
    }
}
