package testingforfun.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import testingforfun.ImagePalets;
import testingforfun.Pixel;
import testingforfun.repo.PictureRepository;

import java.io.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;

@Controller
public class PictureController {
    @Autowired
    private PictureRepository pictureRepository;

    @GetMapping("/all-pictures")
    public String showPicture(Model model) {
        return "picture-parts/all-pictures";
    }

    @PostMapping("/black-white")
    public String blogPostEdit(@RequestParam MultipartFile picture, Model model) throws IOException {
        //Picture pictureNew = new Picture(picture);
        //pictureRepository.save(pictureNew);
        File file = new File("src/main/resources/targetFile.tmp");
        OutputStream os = new FileOutputStream(file);
        os.write(picture.getBytes());
        BufferedImage source = ImageIO.read(file);
        BufferedImage result = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        for (int x = 0; x < source.getWidth(); x++) {
            for (int y = 0; y < source.getHeight(); y++) {

                Color color = new Color(source.getRGB(x, y));

                int blue = color.getBlue();
                int red = color.getRed();
                int green = color.getGreen();
                int grey = (int) (red * 0.299 + green * 0.587 + blue * 0.114);

                int newRed = grey;
                int newGreen = grey;
                int newBlue = grey;

                Color newColor = new Color(newRed, newGreen, newBlue);

                result.setRGB(x, y, newColor.getRGB());
            }
        }
        ImageIO.write(result, "jpg", file);
        Path path = Paths.get("src/main/resources/targetFile.tmp");
        byte[] content = Files.readAllBytes(path);
        MultipartFile mockMultipartFile = new MockMultipartFile("file.tmp", content);
        byte[] fileContent = mockMultipartFile.getBytes();
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        byte[] baseContent = picture.getBytes();
        String baseEncodedString = Base64.getEncoder().encodeToString(baseContent);
        model.addAttribute("newImage", encodedString);
        model.addAttribute("image", baseEncodedString);
        return "picture-parts/black-white";
    }

    @PostMapping("/examples-test")
    public String TestEx(@RequestParam MultipartFile pictureTest,Model model) throws IOException {
        ImagePalets imagePalets = new ImagePalets();
        File file = new File("src/main/resources/newTargetFile.png");
        OutputStream os = new FileOutputStream(file);
        os.write(pictureTest.getBytes());
        String nom = "src/main/resources/newTargetFile.png";

        BufferedImage image = imagePalets.load(nom);
        ArrayList<Pixel> L = imagePalets.GetPixelsFromImage(image);
        int k = 5;
        ArrayList<Pixel> Lk = imagePalets.kmeans(L, k);
        BufferedImage palette = imagePalets.MakePalette(Lk, k, image);
        BufferedImage res = imagePalets.Fusion(image, palette);
        imagePalets.Save(res, nom+"_Palette.png");

        Path path = Paths.get(nom+"_Palette.png");
        byte[] content = Files.readAllBytes(path);
        String encodedString = Base64.getEncoder().encodeToString(content);
        model.addAttribute("image",encodedString);
        // System.exit(0);

        System.out.println("Finished.");
        return "picture-parts/examples-test";
    }
}
