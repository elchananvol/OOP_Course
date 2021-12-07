package ascii_art.img_to_char;

import image.Image;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;

public class BrightnessImgCharMatcher {
    private  String font = "Ariel";
    private final Image image;

    public BrightnessImgCharMatcher(Image image, String font){
        this.image = image;
        this.font = font;
    }
    public char[][] chooseChars(int numCharsInRow, Character[] charSet){
        return convertImageToAscii(numCharsInRow,this.image,charSet);



    }
    public float[] brightnessLevel(Character[] charSet){
        float[] to_ret = new float[charSet.length];
        for(int i=0;i<charSet.length;i++){
            boolean[][] cs = CharRenderer.getImg(charSet[i], 16,this.font );
            int counter =0;
//            int counterall =0 ;
            for(int j=0;j<cs.length;j++){
                for(int k=0;k<cs[j].length;k++){
                    if(cs[j][k]){
                        counter++;
                    }
                }
//                counterall += cs[j].length;
            }
            to_ret[i]= (float)counter / (float)(cs.length * cs[0].length) ;


        }
        return to_ret;
    }

    public static float[] quantization(float[] brightness){
        float max = brightness[0];
        float min = brightness[0];
        float[] new_brightness = new float[brightness.length];
        for (int ktr = 0; ktr < brightness.length; ktr++) {
            if (brightness[ktr] > max) {
                max = brightness[ktr];
            }
            if (brightness[ktr] <min) {
                min = brightness[ktr];
            }

        }
        assert min != max;
        float maxMinusMin = max - min;
        for (int ktr = 0; ktr < brightness.length; ktr++) {
            new_brightness[ktr] = (brightness[ktr]-min)/maxMinusMin;
        }
        return new_brightness;
    }


    public static float meanPixel(Image img){
        int counterOfPixels = 0;
        double sumOfPixelsValues =0;
        for (Color pixel: img.pixels()){
            sumOfPixelsValues += rgbToGrey(pixel);
            counterOfPixels++;

        }
        return (float) (sumOfPixelsValues / (counterOfPixels *255));

        }
    public static double rgbToGrey(Color color){
        return  color.getRed() * 0.2126 + color.getGreen() * 0.7152 +
                color.getBlue() * 0.0722;

    }
    private char[][] convertImageToAscii(int numCharsInRow,Image img,Character[] charSet){
        float[] b =  brightnessLevel(charSet);
        b= quantization(b);
        int pixels = img.getWidth() / numCharsInRow;
        char[][] asciiArt = new char[img.getHeight()/pixels][img.getWidth()/pixels];
        int counterInRow = -1;
        int counterInCol = -1;
        for(Image subImage : img.squareSubImagesOfSize(pixels)) {
            counterInCol ++;
            if (counterInCol % numCharsInRow ==0)
            {
                counterInCol =0;
                counterInRow ++;
            }

            float mean  =meanPixel(subImage);
            float nearest = b[0];
            int idx = 0;
            for (int i = 0; i<b.length;i++){
                if(Math.abs(nearest- mean) > Math.abs(b[i] - mean)){
                    nearest = b[i];
                    idx=i;
                }
            }
            asciiArt[counterInRow][counterInCol] = charSet[idx];
        }
        return asciiArt;
    }



}
