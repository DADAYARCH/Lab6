package server.net;

import client.net.Request;
import client.net.Response;
import client.net.Transfer;
import client.net.TransferException;

import java.net.SocketException;

public class ClientConnection {
    Transfer objectTransfer;

    public ClientConnection(){
        try {
            objectTransfer = new Transfer(1596);
        }catch (SocketException e){
            throw new TransferException();
        }
    }
    public ClientConnection(int port){
        try{
            objectTransfer = new Transfer(port);
        }catch (SocketException e) {
            throw new TransferException();
        }
    }

    public Request receive(){
        return (Request) objectTransfer.receive();
    }

    public void sendResponse(Response response){
        objectTransfer.send(response);
    }
}
