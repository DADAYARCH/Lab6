package client.io;

import java.util.Scanner;

public class ConsoleInput implements InputHandler{
    Scanner scanner;

    public ConsoleInput(){scanner = new Scanner(System.in);}
    public String nextLine() {
        return scanner.nextLine();
    }


    public boolean hasNext() {
        return scanner.hasNextLine();
    }
}
