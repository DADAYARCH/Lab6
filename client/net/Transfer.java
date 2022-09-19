package client.net;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Transfer {
    DatagramSocket socket;
    InetAddress targetAddress;
    int targetPort;

    public Transfer(int localPort, InetAddress targetAddress, int targetPort) throws SocketException{
        this.targetAddress = targetAddress;
        this.targetPort = targetPort;
        setSocket(localPort);
    }

    public Transfer(int localPort) throws SocketException{
        setSocket(localPort);
    }

    private void setSocket(int port)throws SocketException{
        socket = new DatagramSocket(port);
        socket.setSoTimeout(10000);
    }

    public void send(Serializable object) {
        try {
            FileOutputStream fos = new FileOutputStream("buffer.bin");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();

            FileChannel fileChannel = FileChannel.open(Paths.get("buffer.bin"));
            long packetCount = fileChannel.size() / 1000L + 1;
            sendPacketCount(packetCount);
            while (packetCount != 0) {
                ByteBuffer buffer = ByteBuffer.allocate(1000);
                fileChannel.read(buffer);
                sendPart(buffer.array());
                --packetCount;
            }
        } catch (UnknownHostException e) {
            throw new TransferException();
        } catch (IOException e) {
            throw new TransferException();
        }
    }

    private void sendPart(byte[] buffer) throws IOException{
        byte[] bufferSize = intToByteArray(buffer.length);

        // local port
        DatagramPacket packet = new DatagramPacket(bufferSize, 4, targetAddress, targetPort);
        socket.send(packet);

        packet = new DatagramPacket(buffer, buffer.length, targetAddress, targetPort);
        socket.send(packet);
    }

    private void sendPacketCount(Long packetCount) {
        try {
            byte[] buffer = longToByteArray(packetCount);
            DatagramPacket packet = new DatagramPacket(buffer, 8, targetAddress, targetPort);
            socket.send(packet);
        } catch (IOException e) {
            throw new TransferException();
        }
    }

    private long receivePacketCount() {
        try {
            byte[] buffer = new byte[8];
            DatagramPacket packet = new DatagramPacket(buffer, 8);
            socket.receive(packet);
            return byteArrayToLong(packet.getData());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new TransferException();
        }
    }

//    public void sendPart(ByteArrayInputStream)

    public Object receive() {

        try {
            FileChannel fileChannel = FileChannel.open(Paths.get("buffer2.bin"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

            long packetCount = receivePacketCount();

            while (packetCount != 0) {
                fileChannel.write(ByteBuffer.wrap(receivePart()));
                --packetCount;
            }

            fileChannel.close();
            FileInputStream fis = new FileInputStream("buffer2.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object obj = ois.readObject();
            ois.close();
            return obj;
        } catch (IOException e) {
            throw new TransferException();
        } catch (ClassNotFoundException e) {
            throw new TransferException();
        }
    }

    private byte[] receivePart() throws IOException {
        byte[] bufferSizeInBytes = new byte[4];
        DatagramPacket packet = new DatagramPacket(bufferSizeInBytes, 4);
        socket.receive(packet);
        targetAddress = packet.getAddress();
        targetPort = packet.getPort();

        int bufferSize = byteArrayToInt(packet.getData());
        byte[] buffer = new byte[bufferSize];
        packet = new DatagramPacket(buffer, bufferSize);
        socket.receive(packet);

        return packet.getData();
    }

    private byte[] intToByteArray(int number) {
        byte[] data = new byte[4];
        for (int i = 0; i < 4; ++i) {
            int shift = i << 3; // i * 8
            data[3-i] = (byte)((number & (0xff << shift)) >>> shift);
        }
        return data;
    }

    private byte[] longToByteArray(long number) {
        byte[] data = new byte[8];
        for (int i = 0; i < 8; ++i) {
            int shift = i << 3; // i * 8
            data[7-i] = (byte)((number & (0xff << shift)) >>> shift);
        }
        return data;
    }

    private int byteArrayToInt(byte[] data) {
        int number = 0;
        for (int i = 0; i < 4; ++i) {
            number |= (data[3-i] & 0xff) << (i << 3);
        }
        return number;
    }

    private int byteArrayToLong(byte[] data) {
        int number = 0;
        for (int i = 0; i < 8; ++i) {
            number |= (data[7-i] & 0xff) << (i << 3);
        }
        return number;
    }

    public void close() {
        socket.close();
    }
}


































