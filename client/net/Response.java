package client.net;

import java.io.Serializable;

public class Response implements Serializable {
    private Object printableObject;
    private boolean objectIsMessageCode;

    public Response(){};

    public Response(Object printableObject){
        this.printableObject = printableObject;
    }
    public Response(String messageCode, boolean isMessageCode){
        this.printableObject = messageCode;
        this.objectIsMessageCode = isMessageCode;
    }

    public boolean printObject(){
        return printableObject != null;
    }

    public boolean printMessage() {
        return objectIsMessageCode;
    }

    public Object getPrintableObject(){
        return printableObject;
    }
}
