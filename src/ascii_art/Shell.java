package ascii_art;

import ascii_art.img_to_char.BrightnessImgCharMatcher;
import ascii_output.HtmlAsciiOutput;
import image.Image;

import java.util.*;
import java.util.stream.Stream;

public class Shell {
    private final Image img;
    private Set<Character> charSet;
    private final int minCharsInRow;
    private final int maxCharsInRow;
    private int charsInRow;
    private static final int INITIAL_CHARS_IN_ROW = 64;

    private static char[] parseCarRange(String param){
        switch (param){
            case "all":
                return new char[]{' ', '~'};
            case"space":
                return new char[]{' ', ' '};
                default:
                    char[] theStringInArray = param.toCharArray();
                    if(theStringInArray.length == 1)
                        return new char[]{theStringInArray[0] , theStringInArray[0]};
                    if(theStringInArray.length > 3  || theStringInArray.length < 3 || theStringInArray[1]!='-')
                return null;
                    if(theStringInArray[0] > theStringInArray[2])
                        return null;
                    return new char[]{theStringInArray[0] , theStringInArray[2]};
        }
    }
    public Shell(Image img) {
        this.img = img;
        this.charSet=new HashSet<>();
        minCharsInRow = Math.max(1, img.getWidth()/img.getHeight());
        maxCharsInRow = img.getWidth() / minCharsInRow; // not sure if it's right
        charsInRow = Math.max(Math.min(INITIAL_CHARS_IN_ROW, maxCharsInRow), minCharsInRow);

    }

    public void run(){
        Scanner scanner= new Scanner(System.in);
        String input;
        System.out.print(">>>");
        while(true){
            input=scanner.nextLine();
            switch (input){
                case "exit":
                    return;
                case "chars":
                    printChars();
                    System.out.print(">>>");
                    break;
                case "render":
                    render();
                    System.out.print(">>>");
                    break;
                default:
                    if(input.startsWith("add"))
                        addChars(input);
                    if(input.startsWith("remove"))
                        removeChars(input);
                    if(input.startsWith("res"))
                        resChange(input);
                        System.out.print(">>>");
            }

        }
    }

    private void render() {
        BrightnessImgCharMatcher charMatcher = new BrightnessImgCharMatcher(img, "Courier New");
        HtmlAsciiOutput asciiOutput = new HtmlAsciiOutput("out2.html", "Courier New");
        var charsInARow = img.getWidth() / 2;
        Character[] characters = new Character[charSet.size()];
        int i=0;
        for(Character charc : charSet){
            characters[i++] = charc;
        }
        char[][] chars = charMatcher.chooseChars(charsInARow, characters);
        asciiOutput.output(chars);
    }

    private void removeChars(String inputString) {
        char[] range = parseCarRange(inputString.replaceAll("remove", " ").trim());
        if(range != null){
            Stream.iterate(range[0], c -> c <= range[1], c -> (char) (c + 1)).forEach(charSet::remove);
        }
    }

    private void addChars(String inputString) {
        char[] range = parseCarRange(inputString.replaceAll("add", " ").trim());
        if(range != null){
            Stream.iterate(range[0], c -> c <= range[1], c -> (char)((int)c+1)).forEach(charSet::add);
        }
    }



    private void printChars(){
        charSet.stream().sorted().forEach(c-> System.out.print(c + " "));
        System.out.println();
    }
    private void resChange(String s){
        switch (s.replace("res" , "").trim()){
            case "up":
                if(charsInRow >= maxCharsInRow){
                    System.out.println("you are at the max limit");
                    return;
                }
                charsInRow *=2;
                break;
            case "down":
                if(charsInRow <= maxCharsInRow){
                    System.out.println("you are at the min limit");
                    return;
                }
                charsInRow /=2;
                break;
        }
        System.out.println(String.format("current res %o" , charsInRow));
    }
}
