package client.net;

import java.io.*;
import java.net.*;

public class ObjectTransfer {
    DatagramSocket socket;
    InetAddress targetAddress;
    int targetPort;

    public ObjectTransfer(int localPort, InetAddress targetAddress, int targetPort) throws SocketException{
        this.targetAddress = targetAddress;
        this.targetPort = targetPort;
        setSocket(localPort);
    }

    public void setSocket(int port) throws SocketException{
        socket = new DatagramSocket(port);
        socket.setSoTimeout(1000);
    }

    public void  send(Serializable object){
        try {
            byte[] buffer = getSerializedObjectToBytes(object);
            byte[] buferSize = intToByteArray(buffer.length);

            //local port
            DatagramPacket packet = new DatagramPacket(buferSize, 4, targetAddress, targetPort);
            socket.send(packet);

            packet = new DatagramPacket(buffer,buffer.length, targetAddress, targetPort);
            socket.send(packet);
        }catch (UnknownHostException e ){
            throw new TransferException();
        }catch (IOException e){
            throw new TransferException();
        }
    }

    public Object receive() {
        try {
            byte[] bufferSizeInBytes = new byte[4];
            DatagramPacket packet = new DatagramPacket(bufferSizeInBytes, 4);
            socket.receive(packet);
            targetAddress = packet.getAddress();
            targetPort = packet.getPort();

            int bufferSize = byteArrayToInt(packet.getData());
            byte[] buffer = new byte[bufferSize];
            packet = new DatagramPacket(buffer, bufferSize);
            socket.receive(packet);

            return getDeserializedObjectFromBytes(packet.getData());
        } catch (IOException e) {
            throw new TransferException();
        }
    }

    private byte[] getSerializedObjectToBytes(Serializable object){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.flush();
            return baos.toByteArray();
        }catch (IOException e){
            throw new TransferException();
        }
    }

    private Object getDeserializedObjectFromBytes(byte[] bytes){
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Object obj = ois.readObject();
            ois.close();
            return obj;
        }catch (ClassNotFoundException e){
            throw new TransferException();
        } catch (IOException e) {
        throw new TransferException();
    }
    }

    private byte[] intToByteArray(int number){
        byte[] data = new byte[4];
        for (int i = 0; i<4; ++i){
            int shift = i << 3; // i * 8
            data[3-i] = (byte)((number & (0xff << shift)) >>> shift);
        }
        return data;
    }

    private int byteArrayToInt(byte[] data){
        int number = 0;
        for (int i = 0; i < 4; ++i){
            number |= (data[3-i] & 0xff) << (i << 3);
        }
        return number;
    }

    public void close(){
        socket.close();
    }
}
