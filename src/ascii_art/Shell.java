package ascii_art;

import image.Image;

import java.util.Scanner;

public class Shell {

    public Shell(Image img) {

    }

    public void run(){
        Scanner scanner= new Scanner(System.in);
        System.out.print(">>>");
        while(!scanner.nextLine().equals("exit")){
            System.out.print(">>>");
        }
    }
}
