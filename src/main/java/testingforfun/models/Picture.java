package testingforfun.models;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.File;

@Entity
public class Picture {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Lob
    @Column(name = "photo", columnDefinition="BLOB")
    MultipartFile file;
    Picture(){

    }

    public Picture(MultipartFile file) {
        this.file = file;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
