package ascii_art.img_to_char;

import java.awt.*;
import java.util.Arrays;
import image.Image;
import tools.Tools;

import static tools.Tools.*;

public class BrightnessImgCharMatcher {
    private Image image;
    private String font;

    public BrightnessImgCharMatcher(Image image , String font){
        this.image = image;
        this.font = font;
    }

    public char[][] chooseChars(int numCharsInRow , Character[] charSet){ // אמרו להשתמש ב Character אבל במקום השתמשתי בצאר
       return convertImageToAscii(toPrimitiveCharArray(charSet) , giveBrightnessLevel(toPrimitiveCharArray(charSet)) , numCharsInRow);

    }
    private  double[] giveBrightnessLevel(char[] chars ){
        int numberOfWhites;
        double[] brightnessLevel = new double[chars.length];
        for (int i = 0; i < chars.length; i++) {
            numberOfWhites=0;
            boolean[][] blackOrWhite = CharRenderer.getImg(chars[i], 16 , font);
            for (int row = 0; row < blackOrWhite.length; row++) {
                for (int col = 0; col < blackOrWhite[row].length; col++) {
                    if (blackOrWhite[row][col]) {
                        numberOfWhites++;
                    }
                }

            }
            brightnessLevel[i] = ((double) numberOfWhites / (blackOrWhite.length* blackOrWhite.length));
        }

        return normalizeCharBrightness(brightnessLevel);
    }
    private double[] normalizeCharBrightness(double[] notNormalized){
        double[] normalized = new double[notNormalized.length];
        Arrays.sort(notNormalized);
        for (int i = 0; i < notNormalized.length; i++) {
            normalized[i] = (notNormalized[i]-notNormalized[0])/(notNormalized[notNormalized.length-1]-notNormalized[0]);
        }
        return normalized;
    }
    private double imgAvgBrightnessLevel(Image subImage){
        double sumPixelBrightness=0 , numOfpixels=0;
        for (Color pixel: subImage.pixels()){
            sumPixelBrightness += (double)pixel.getRed()/255 * 0.2126 + (double)pixel.getGreen()/255 * 0.7152 + (double)pixel.getBlue()/255 * 0.0722;
            numOfpixels++;
        }

        return ( sumPixelBrightness/numOfpixels);
    }
    private  char[][] convertImageToAscii(char[] charsForUse , double[] suitableBrightnessLevel , int numOfCharsInARow){
        int pixels = image.getWidth() / numOfCharsInARow;
        char[][] asciiArt = new char[image.getHeight()/pixels][image.getWidth()/pixels];
        int closestValueIndex , rowTracker = 0 , colTracker =0;
        for(Image subImage : image.squareSubImagesOfSize(pixels)) {
            closestValueIndex = 0;
                for (int i = 0; i < suitableBrightnessLevel.length; i++) {
                    if(Math.abs(imgAvgBrightnessLevel(subImage)-suitableBrightnessLevel[i]) <= Math.abs(imgAvgBrightnessLevel(subImage)-suitableBrightnessLevel[closestValueIndex]))
                        closestValueIndex = i;
                }
            asciiArt[colTracker][rowTracker] = charsForUse[closestValueIndex];
            colTracker++;
            if(colTracker == numOfCharsInARow){
                colTracker=0;
                rowTracker++;
            }
        }
        return asciiArt;
    }

}

