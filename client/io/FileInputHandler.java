package client.io;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileInputHandler implements InputHandler{
    Scanner scanner;

    public FileInputHandler(String fileName){
        try {
            scanner = new Scanner(Paths.get(fileName));
        }catch (IOException e){
            throw new IOHandlerException();
        }
    }

    @Override
    public String nextLine() {
        return null;
    }

    @Override
    public boolean hasNext() {
        return false;
    }
}
