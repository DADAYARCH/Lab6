package client.io;

import client.utils.Message;

public class ConsoleOutput implements OutputHandler{

    @Override
    public void printMessage(String messageCode) {
        System.out.print(Message.getMessage(messageCode));
    }

    @Override
    public void alwaysPrintln(Object object) {
        System.out.println(object);
    }
}
