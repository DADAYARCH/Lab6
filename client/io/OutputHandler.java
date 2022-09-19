package client.io;

public interface OutputHandler {
    void printMessage(String messageCode);
    void alwaysPrintln(Object object);
}
