package testingforfun;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImagePalets {


    public static void main(String[] args){

        String nom = "photo3.jpg";

        BufferedImage image = load(nom);
        ArrayList<Pixel> L = GetPixelsFromImage(image);
        System.out.println("Entrer le k...");
        int k = 6;


        ArrayList<Pixel> Lk = kmeans(L, k);

        BufferedImage palette = MakePalette(Lk, k, image);
        BufferedImage res = Fusion(image, palette);
        Save(res, nom+"_Palette.png");
        show(res);

    }
    static void assigningMembership(ArrayList<Pixel> L, ArrayList<Pixel> Lk, int K){
        int T = L.size();
        for (int t=0; t<T; t++){
            L.get(t).membership = Lk.get(0);
            for (int k=0; k<K; k++){
                if(Pixel.EuclideanDistance(L.get(t), L.get(t).membership) > Pixel.EuclideanDistance(L.get(t), Lk.get(k))){
                    L.get(t).membership = Lk.get(k);
                }
            }
        }
    }

    static ArrayList<Pixel> newPalette(int K, ArrayList<Pixel> L, ArrayList<Pixel> Lk){
        ArrayList<Pixel> R = MakeKRandomPixels(K);
        int T = L.size();
        for (int k=0; k<K; k++){
            Pixel Avg = new Pixel(0,0,0);
            int avgR = 0;
            int avgG = 0;
            int avgB = 0;
            int total = 0;
            for (int t=0; t<T; t++){
                if (L.get(t).membership==Lk.get(k)){
                    total++;
                    avgR += L.get(t).red;
                    avgG += L.get(t).green;
                    avgB += L.get(t).blue;
                }
            }
            if (total!=0){
                Avg.red = avgR/total;
                Avg.green = avgG/total;
                Avg.blue = avgB/total;

                R.set(k, Avg);
            }
        }
        return R;
    }

    static String toString(ArrayList<Pixel> L){
        String s = "";
        for(int i=0; i<L.size(); i++){
            s+= L.get(i).red + L.get(i).green + L.get(i).blue;
        }
        return s;
    }

    public static ArrayList<Pixel> kmeans(ArrayList<Pixel> L, int K){
        ArrayList<Pixel> Lk = MakeKRandomPixels(K);
        int T = L.size();
        int i = 0;
        int counter = 0;
        while(true){
            System.out.println("Itération n°"+i);

            assigningMembership(L, Lk, K);


            ArrayList<Pixel> newPalette = newPalette(K, L, Lk);



            String a = toString(Lk);
            String b = toString(newPalette);

            if(a.equals(b)){
                System.out.println("Convergence");
                break;
            }

            Lk = newPalette;
            i++;

            if (i==1000){
                System.out.println("Limite atteinte ! ************\n Voici Lk");
                PrintPixelsList(Lk);
                break;
            }

        }
        System.out.println("***************************");
        return Lk;
    }

    static ArrayList<Pixel> MakeKRandomPixels(int k){
        ArrayList<Pixel> Rk = new ArrayList<Pixel>();
        Pixel rp = new Pixel(0,0,0);
        for (int i=0; i<k; i++){
            rp.red = (int) (Math.random()*255);
            rp.green = (int) (Math.random()*255);
            rp.blue = (int) (Math.random()*255);
            Rk.add(rp);
            rp = new Pixel(0,0,0);
        }
        return Rk;
    }

    public static BufferedImage load(String chemin){
        File input = new File(chemin);
        BufferedImage image = null;
        try{
            image = ImageIO.read(input);
        }catch(IOException e){
            System.out.println("Can't load the image!");
        }
        return image;
    }

    static void PrintPixelsList(ArrayList<Pixel> L){
        Pixel p;
        for (int i=0; i<L.size(); i++){
            p = L.get(i);
            System.out.format("Red : %d --- Green : %d --- Blue : %d\n", p.red, p.green, p.blue);
        }
    }

    public static ArrayList<Pixel> GetPixelsFromImage(BufferedImage image){
        int width = image.getWidth();
        int height = image.getHeight();
        ArrayList<Pixel> L = new ArrayList();
        int red;
        int green;
        int blue;
        Pixel p;
        System.out.println("Loading pixels...");
        for (int i=0; i<height; i++){
            for (int j=0; j<width; j++){
                Color c = new Color(image.getRGB(j, i));
                red = c.getRed();
                green = c.getGreen();
                blue = c.getBlue();
                p = new Pixel(red, green, blue);
                L.add(p);
            }
        }
        System.out.println("All pixels loaded!");
        return L;
    }

    public static void show(BufferedImage image){
        int width = (int) (image.getWidth()*1.2);
        int height = (int) (image.getHeight()*1.2);
        JFrame frame = new JFrame();
        JLabel lblimage = new JLabel(new ImageIcon(image));

        frame.getContentPane().add(lblimage, BorderLayout.CENTER);
        frame.setSize(width, height);
        frame.setVisible(true);

    }

    static void OneColor(Pixel p, int start, int end, int lenght, BufferedImage Palette){

        int alpha = 255;
        int red = p.red;
        int blue = p.blue;
        int green = p.green;
        Color c = new Color(red, green, blue);
        for (int i=start; i<end; i++){
            for (int j=0; j<lenght; j++){

                Palette.setRGB(i, j, c.getRGB());

            }
        }
    }

    public static BufferedImage MakePalette(ArrayList<Pixel> Lk, int k, BufferedImage image){
        int width = image.getWidth();
        int hight = image.getHeight();
        BufferedImage Palette = new BufferedImage(width, (int)(hight/4), 1);
        int pas = (int) (width/k);
        for (int i=0; i<k; i++){
            OneColor(Lk.get(i), i*pas, (i+1)*pas, (int)(hight/4), Palette);
            System.out.println("Making i="+i+"-"+Lk.get(i).red+"--"+Lk.get(i).blue+"--"+Lk.get(i).green);
        }

        return Palette;
    }

    public static BufferedImage Fusion(BufferedImage image, BufferedImage Palette){
        int width = image.getWidth();
        int hight = image.getHeight() + Palette.getHeight();
        int type = image.getType();
        int rgbValues;
        BufferedImage res = new BufferedImage(width, hight, type);
        for (int i=0; i<width; i++){
            for (int j=0; j<image.getHeight(); j++){
                rgbValues = image.getRGB(i, j);
                res.setRGB(i, j, rgbValues);
            }
        }
        for (int i=0; i<width; i++){
            for (int j=image.getHeight(); j<Palette.getHeight()+image.getHeight(); j++){
                rgbValues = Palette.getRGB(i, j-image.getHeight());
                res.setRGB(i, j, rgbValues);
            }
        }
        return res;
    }

    public static void Save(BufferedImage image, String name){
        File outputfile = new File(name);
        try {
            int type = image.getType();
            ImageIO.write(image, "png", outputfile);
        } catch (IOException ex) {
            Logger.getLogger(ImagePalets.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void run(){
        float epsilon = (float) Math.pow(10, -6);
        int n = 1;
        double Un = Math.random();
        double Un1 = Un;
        while(true){
            Un1 = Math.random();
            if(Un == Un1) break;
            System.out.println(n);
            n++;
            if(n==100) break;
        }
    }
}