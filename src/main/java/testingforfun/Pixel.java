package testingforfun;

public class Pixel {
    int red;
    int green;
    int blue;
    Pixel membership;

    Pixel(int r, int g, int b){
        this.red = r;
        this.green = g;
        this.blue = b;
    }


    static double EuclideanDistance(Pixel a, Pixel c){
        double dist = 0;
        double r = Math.pow((a.red - c.red),2);
        double g = Math.pow((a.green - c.green),2);
        double b = Math.pow((a.blue - c.blue),2);
        dist = Math.sqrt(r + g + b );
        return dist;
    }



}