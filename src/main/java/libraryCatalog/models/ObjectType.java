package libraryCatalog.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(indexes = @Index(columnList = "objecttypeid"))
public class ObjectType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long objecttypeid;

    @OneToMany(mappedBy="objectType")
    private List<Object> objects;


    @ManyToMany(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinTable(name = "attrebutes",
            joinColumns = @JoinColumn(name = "objecttypeid"),
            inverseJoinColumns = @JoinColumn(name = "attrebutid")
    )
    private Collection<Attrebutes> attrebutes;


    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String description;


    public ObjectType() {
    }

    public ObjectType(List<Object> objects, Collection<Attrebutes> attrebutes, String name, String description) {
        this.objects = objects;
        this.attrebutes = attrebutes;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return objecttypeid;
    }

    public void setObjecttypeid(Long objecttypeid) {
        this.objecttypeid = objecttypeid;
    }

    public List<Object> getObjects() {
        return objects;
    }

    public void setObjects(List<Object> objects) {
        this.objects = objects;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Attrebutes> getAttrebutes() {
        return attrebutes;
    }

    public void setAttrebutes(Collection<Attrebutes> attrebutes) {
        this.attrebutes = attrebutes;
    }

    @Override
    public String toString() {
        return "name=" + name +
                ", description=" + description;
    }
}
