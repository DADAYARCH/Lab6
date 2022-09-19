package client.io;

public class HiddenOutput implements OutputHandler{
    @Override
    public void printMessage(String messageCode) {

    }

    @Override
    public void alwaysPrintln(Object object) {
        System.out.println(object);
    }
}
