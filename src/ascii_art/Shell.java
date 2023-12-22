package ascii_art;

import image.Image;

import java.util.*;
import java.util.stream.Stream;

public class Shell {
        private Set<Character> charSet;

    private static char[] parseCarRange(String param){
        switch (param){
            case "all":
                return new char[]{' ', '~'};
            case"space":
                return new char[]{' ', ' '};
                default:
                    char[] theStringInArray = param.toCharArray();
                    if(theStringInArray.length > 3  || theStringInArray.length < 3 || theStringInArray[1]!='-')
                return null;
                    if(theStringInArray[0] > theStringInArray[2])
                        return null;
                    return new char[]{theStringInArray[0] , theStringInArray[2]};
        }
    }
    public Shell(Image img) {
        this.charSet=new HashSet<>();
        charSet.add('e');
        Collections.addAll(charSet , 'c' , 'b' , 'a' , 'd' , '6' , '5');

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
                default:
                    if(input.startsWith("add"))
                        addChars(input);
                    if(input.startsWith("remove"))
                        removeChars(input);
                        System.out.print(">>>");
            }

        }
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
        charSet.forEach(System.out::println);
    }
}
