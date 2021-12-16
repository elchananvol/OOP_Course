package ascii_art;
import image.Image;
import java.util.logging.Logger;

public class Driver {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("USAGE: java asciiArt ");
            return;
        }
        Image img = Image.fromFile(args[0]);
        if (img == null) {
            Logger.getGlobal().severe("Failed to open image file " + args[0]);
            return;
        }
//        int[] arr = {1,2,3,4,5,3};
//        System.out.println(src.ascii_art.Algorithms.findDuplicate(arr));
        new Shell(img).run();


//        Character[] charSet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t'
//                ,'y','x','u','v',' ','\''};
//        Character[] charSet = {'a', 'b', 'c', 'd'};
//        Image img = Image.fromFile("ex4/dino.png");
//        BrightnessImgCharMatcher charMatcher = new BrightnessImgCharMatcher(img, "Courier New");
//        HtmlAsciiOutput asciiOutput = new HtmlAsciiOutput("ex4/out1.html", "Courier New");
//        var charsInARow = img.getWidth() / 8;
//        char[][] chars = charMatcher.chooseChars(charsInARow, charSet);
//        asciiOutput.output(chars);
    }
//
//    public static void main(String[] args){
//        boolean[][] cs = CharRenderer.getImg('c', 16, "Ariel");
//        CharRenderer.printBoolArr(cs);

//        String MARIO = "mario";
//            float[] ch = BrightnessImgCharMatcher.brightnessLevel(charst);
//            System.out.println(Arrays.toString(ch));
//            System.out.println(Arrays.toString(BrightnessImgCharMatcher.quantization(ch)));

//        Image image1 = Image.fromFile("ex4/testSrc/mario.jpeg");
//        //System.out.println(BrightnessImgCharMatcher.meanPixel(image));
//
//        BrightnessImgCharMatcher charMatcher = new BrightnessImgCharMatcher(image1, "Ariel");
//        System.out.println(image1.getWidth());
//        var chars = charMatcher.chooseChars(563, charst);
//        System.out.println(Arrays.deepToString(chars));

//        Character[] charSet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t'
//                ,'y','x','u','v',' ','\''};
//        Character[] charst = {'m','o'};
//        Image image = Image.fromFile("ex4/board.jpeg");
//        BrightnessImgCharMatcher b = new BrightnessImgCharMatcher(image,"ariel");
//        System.out.println(Arrays.deepToString( b.chooseChars(2,charst)));
////
//
//
//    }
}
