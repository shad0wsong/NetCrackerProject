package libraryCatalog.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
public class Object {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long objectid;

    @ManyToOne(optional=false, cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn (name="objecttypeid")
    private ObjectType objectType;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String name;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "valueid")
    private Value value;


    public Object() {
    }

    public Object(ObjectType objectType, String name) {
        this.objectType = objectType;
        this.name = name;

    }
    public Object(ObjectType objectType, String name, Value value) {
        this.objectType = objectType;
        this.name = name;
        this.value = value;
    }

    public Long getId() {
        return objectid;
    }

    public void setObjectid(Long objectid) {
        this.objectid = objectid;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "name=" + name;
    }
}
