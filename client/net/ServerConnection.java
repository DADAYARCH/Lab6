package client.net;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ServerConnection {
    Transfer objectTransfer;

    public ServerConnection() {
        int localPort = 1390;
        while (true){
            try {
                objectTransfer = new Transfer(localPort, InetAddress.getLocalHost(),1596);
                break;
            } catch (SocketException e) {
                ++localPort;
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
    }

    public ServerConnection(int targetPort) {
        int localPort = 1390;
        while (true){
            try {
                objectTransfer = new Transfer(localPort, InetAddress.getLocalHost(),targetPort);
                break;
            } catch (SocketException e) {
                ++localPort;
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
    }

    public ServerConnection(int targetPort, String targetAddress) {
        int localPort = 1390;
        while (true){
            try {
                objectTransfer = new Transfer(localPort, InetAddress.getByName(targetAddress),targetPort);
                break;
            } catch (SocketException e) {
                ++localPort;
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
    }

    public Response get(Request request){
        objectTransfer.send(request);
        return (Response) objectTransfer.receive();
    }
}
