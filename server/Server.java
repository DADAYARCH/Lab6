package server;

import client.net.Request;
import client.net.TransferException;
import server.net.ClientConnection;
import client.models.ParsingError;
import client.models.WorkerCollection;
import server.commands.*;

import java.util.Scanner;


public class Server {
    ClientConnection clientConnection;
    WorkerCollection workerCollection;

    public Server(String path){
        try {
            clientConnection = new ClientConnection();
            workerCollection = new WorkerCollection();
            workerCollection.loadFromJson(path);
        }catch (ParsingError e){
            throw new RuntimeException();
        }
    }

    public Server(String path, int port){
        try {
            clientConnection = new ClientConnection(port);
            workerCollection = new WorkerCollection();
            workerCollection.loadFromJson(path);
        }catch (ParsingError e){
            throw new RuntimeException();
        }
    }

    private Command getCommand(Request request){
        if (request.getCommand().equalsIgnoreCase("clear")){
            return new ClearCommand(workerCollection,clientConnection);
        } else if (request.getCommand().equalsIgnoreCase("count_by_start_date")) {
            return new CountByStartDate(workerCollection,clientConnection,request);
        } else if (request.getCommand().equalsIgnoreCase("exit")) {
            return new ExitCommand(workerCollection,clientConnection);
        } else if (request.getCommand().equalsIgnoreCase("filter_contains_name")) {
            return new FilterContainsNameCommand(workerCollection,clientConnection, request);
        } else if (request.getCommand().equalsIgnoreCase("help")) {
            return new HelpCommand(workerCollection,clientConnection);
        }else if (request.getCommand().equalsIgnoreCase("info")) {
            return new InfoCommand(workerCollection, clientConnection);
        }else if (request.getCommand().equalsIgnoreCase("insert")) {
            return new InsertCommand(workerCollection, clientConnection, request);
        }else if (request.getCommand().equalsIgnoreCase("remove_greater")) {
            return new RemoveGreaterCommand(workerCollection, clientConnection,request);
        }else if (request.getCommand().equalsIgnoreCase("remove_key")) {
            return new RemoveKeyCommand(workerCollection, clientConnection,request);
        }else if (request.getCommand().equalsIgnoreCase("remove_lower")) {
            return new RemoveLowerCommand(workerCollection, clientConnection, request);
        }else if (request.getCommand().equalsIgnoreCase("replace_if_lowe")) {
            return new ReplaceIfLowerCommand(workerCollection, clientConnection,request);
        }else if (request.getCommand().equalsIgnoreCase("show")) {
            return new ShowCommand(workerCollection, clientConnection);
        }else if (request.getCommand().equalsIgnoreCase("update")) {
            return new UpdateCommand(workerCollection, clientConnection,request);
        }else {
            return new UndefinedCommand(workerCollection, clientConnection);
        }
    }

    public void run(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            try {
                Request request = clientConnection.receive();
                Command command = getCommand(request);
                command.execute();
                workerCollection.dumpToJson();
            }catch (TransferException | ParsingError ignore){}
        }
    }

//    public static void main(String[] args) {
//        String path = null;
//        if (args.length == 0) {
//
//        }
//        } else (args.length == 1){
//        new Server(args[0]).run();
//
//        }
//    }
public static void main(String[] args) {
    if (args.length == 0) {
        String path = "C:\\Users\\2003_\\Desktop\\адская дрочь\\X2\\программирование\\проги\\Lab6\\Lab6\\src\\server\\test.json";
        new Server(path).run();
    } else if (args.length == 1)  {
        new Server(args[0]).run();
    } else {
        new Server(args[0], Integer.parseInt(args[1])).run();
    }
}





























}
