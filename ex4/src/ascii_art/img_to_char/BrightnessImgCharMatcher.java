package ascii_art.img_to_char;

import image.Image;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

public class BrightnessImgCharMatcher {
    private static final int NUM_OF_PIXELS_AT_CHARACTER = 16;
    private static final int GREY_RANGE = 255;
    private final String font;
    private final Image image;
    private final HashMap<Image, Double> cache = new HashMap<>();
    private final HashMap<Character, Double> brightnessLevelOfCurrentCharSet = new HashMap<>();
    private final HashMap<Character, Double> brightnessLevelOfLettersCache = new HashMap<>();


    /**
     * constactor. get image and font to replace in the image
     *
     * @param image image
     * @param font  string: "ariel" etc..
     */
    public BrightnessImgCharMatcher(Image image, String font) {
        this.image = image;
        this.font = font;
    }

    /**
     * this function get array of chars, and int numCharsInRow and for every sub image replace the image in
     * the nearest char that have the closest brightness
     *
     * @param numCharsInRow int for num chars that replace row at image
     * @param charSet       list of chars
     * @return if charSet empty, return empty image, else return an image that every sub pixel replaced.
     */
    public char[][] chooseChars(int numCharsInRow, Character[] charSet) {
        if (charSet.length == 0) {
            return new char[1][1];
        }
        int pixels = image.getWidth() / numCharsInRow;
        char[][] asciiArt = new char[image.getHeight() / pixels][image.getWidth() / pixels];
        if (charSet.length == 1) {
            for (char[] arr : asciiArt) {
                Arrays.fill(arr, charSet[0]);
                return asciiArt;
            }
        }
        brightnessLevel(charSet);
        return convertImageToAscii(numCharsInRow, charSet);
    }

    /**
     * this function calculate the brightnessLevel of every char in the charSet and save it in private variable.
     * note: if some char in charSet are calculated at last times then we will use at cache.
     *
     * @param charSet array of chars to calculate
     */
    private void brightnessLevel(Character[] charSet) {
        brightnessLevelOfCurrentCharSet.clear();
        for (Character character : charSet) {
            if (brightnessLevelOfLettersCache.containsKey(character)) {
                brightnessLevelOfCurrentCharSet.put(character, brightnessLevelOfLettersCache.get(character));
            } else {
                boolean[][] cs = CharRenderer.getImg(character, NUM_OF_PIXELS_AT_CHARACTER, this.font);
                int counter = 0;
                for (boolean[] c : cs) {
                    for (boolean b : c) {
                        if (b) {
                            counter++;
                        }
                    }
                }
                brightnessLevelOfLettersCache.put(character,((double) counter / (double) (cs.length * cs[0].length)));
                brightnessLevelOfCurrentCharSet.put(character, brightnessLevelOfLettersCache.get(character));
            }
        }


    }

    /**
     * this function will starch all the brightens of all characters at charset on 0-1 scale
     * according to quantization classic algorithm
     * assert: private brightnessLevel_cache variable are fill with all brightens level of all characters
     * and for them only.
     *
     * @param charSet array of characters
     * @return array at size of charset with values at 0-1 for every character in the same order
     */
    private double[] quantization(Character[] charSet) {
        double max = brightnessLevelOfCurrentCharSet.values().stream().iterator().next();
        double min = max;
        double[] new_brightness = new double[brightnessLevelOfCurrentCharSet.size()];
        for (Character ktr : brightnessLevelOfCurrentCharSet.keySet()) {
            if (brightnessLevelOfCurrentCharSet.get(ktr) > max) {
                max = brightnessLevelOfCurrentCharSet.get(ktr);
            }
            if (brightnessLevelOfCurrentCharSet.get(ktr) < min) {
                min = brightnessLevelOfCurrentCharSet.get(ktr);
            }
        }
        assert min != max;
        double maxMinusMin = max - min;
        for (int ktr = 0; ktr < brightnessLevelOfCurrentCharSet.size(); ktr++) {
            new_brightness[ktr] = (brightnessLevelOfCurrentCharSet.get(charSet[ktr]) - min) / maxMinusMin;
        }
        return new_brightness;
    }

    /**
     * this function get over an image, and calculate the mean of *gray* image.
     *
     * @param img img
     * @return double, the mean pixel.
     */
    private static double meanPixel(Image img) {
        double sumOfPixelsValues = 0;
        for (Color pixel : img.pixels()) {
            sumOfPixelsValues += rgbToGray(pixel);
        }
        return sumOfPixelsValues / (img.getWidth()* img.getHeight() * GREY_RANGE);

    }

    /**
     * convert RGB pixel to its grey value
     *
     * @param color color object
     * @return double value of the grey according RGB to YIQ matrix
     */
    private static double rgbToGray(Color color) {
        return color.getRed() * 0.2126 + color.getGreen() * 0.7152 +
                color.getBlue() * 0.0722;
    }

    /**
     * this function get array of chars, and int numCharsInRow and for every sub image replace the image in
     * the nearest char that have the closest brightness
     *
     * @param numCharsInRow int for num chars that replace row at image
     * @param charSet       list of chars
     * @return return an image that every sub pixel replaced.
     */
    private char[][] convertImageToAscii(int numCharsInRow, Character[] charSet) {
        int pixels = image.getWidth() / numCharsInRow;
        int columns = image.getWidth() / pixels;
        char[][] asciiArt = new char[image.getHeight() / pixels][columns];
        double[] b = quantization(charSet);
        int counter = -1;
        for (Image subImage : image.squareSubImagesOfSize(pixels)) {
            counter++;
            double mean;
            if (!cache.containsKey(subImage)) {
                mean = meanPixel(subImage);
                cache.put(subImage, mean);
            } else {
                mean = cache.get(subImage);
            }
            double nearest = b[0];
            int idx = 0;
            for (int i = 0; i < b.length; i++) {
                if (Math.abs(nearest - mean) > Math.abs(b[i] - mean)) {
                    nearest = b[i];
                    idx = i;
                }
            }
            asciiArt[counter / columns][counter % columns] = charSet[idx];
        }
        return asciiArt;
    }

}








