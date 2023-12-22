import ascii_art.img_to_char.BrightnessImgCharMatcher;
import ascii_output.AsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;

import java.util.Arrays;

public class Main {
    public static void print2dArray(char[][] chars){
        for (int row = 0; row < chars.length; row++) {
            System.out.println(" ");
            for (int col = 0; col < chars[row].length; col++) {
                System.out.print(chars[row][col]);
            }
        }
    }
    public static void main(String[] args) {
        Character[] charSet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','y','x','V','U'};
        Image img = Image.fromFile("dino.png");
        BrightnessImgCharMatcher charMatcher = new BrightnessImgCharMatcher(img, "Courier New");
        HtmlAsciiOutput asciiOutput = new HtmlAsciiOutput("out1.html", "Courier New");
        var charsInARow = img.getWidth() / 2;
        char[][] chars = charMatcher.chooseChars(charsInARow, charSet);
        asciiOutput.output(chars);
        print2dArray(chars);
    }

}