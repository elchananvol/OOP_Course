package ascii_art;

import ascii_art.img_to_char.BrightnessImgCharMatcher;
import ascii_art.img_to_char.CharRenderer;
import image.Image;

import java.util.Arrays;

public class Driver {

    public static void main(String[] args){
        boolean[][] cs = CharRenderer.getImg('c', 16, "Ariel");
        CharRenderer.printBoolArr(cs);
        Character[] charst = {'m','o'};
        String MARIO = "mario";
//        float[] ch = BrightnessImgCharMatcher.brightnessLevel(charst);
//        System.out.println(Arrays.toString(ch));
//        System.out.println(Arrays.toString(BrightnessImgCharMatcher.quantization(ch)));
        Image image = Image.fromFile("ex4/board.jpeg");
        Image image1 = Image.fromFile("ex4/testSrc/mario.jpeg");
        //System.out.println(BrightnessImgCharMatcher.meanPixel(image));
//        System.out.println(Arrays.deepToString(BrightnessImgCharMatcher.convertImageToAscii(2,image,charst)));
        BrightnessImgCharMatcher charMatcher = new BrightnessImgCharMatcher(image1, "Ariel");
        System.out.println(image1.getWidth());
//        var chars = charMatcher.chooseChars(563, charst);
//        System.out.println(Arrays.deepToString(chars));


    }
}
