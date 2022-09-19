package client;

import client.io.*;
import client.models.Worker;
import client.models.WorkerForm;
import client.net.Request;
import client.net.Response;
import client.net.ServerConnection;
import client.net.TransferException;
import client.utils.PrettyPrint;

import java.io.IOException;
import java.util.ArrayList;

public class Client {
    private InputHandler input;
    private OutputHandler output;
    private ServerConnection serverConnection;
     private boolean active = true;
     private static ArrayList<String> paths = new ArrayList<>();

     public Client(){
         input = new ConsoleInput();
         output = new ConsoleOutput();
         serverConnection = new ServerConnection();
     }

     public Client(int port){
         input = new ConsoleInput();
         output = new ConsoleOutput();
         serverConnection = new ServerConnection(port);
     }

    public Client(int port, String targetAddres){
        input = new ConsoleInput();
        output = new ConsoleOutput();
        serverConnection = new ServerConnection(port, targetAddres);
    }

    public Client(Client client, String fileName ){
         try {
             input = new FileInput(fileName);
             output = new HiddenOutput();
             serverConnection = client.serverConnection;
         }catch (IOException e){
             active = false;
         }
    }

    public void executeCommand(String command, Worker worker){
        Request request = new Request(command,worker);
        Response response = serverConnection.get(request);
        if (response.printMessage()){
            output.printMessage((String) response.getPrintableObject());
        } else if (response.printObject()) {
            new PrettyPrint(output).print(response.getPrintableObject());
        }
    }

    public void executeCommand(String command){
         Request request = new Request(command);
         Response response = serverConnection.get(request);
         if (response.printMessage()){
             output.printMessage((String) response.getPrintableObject());
         }else if (response.printObject()){
             new PrettyPrint(output).print(response.getPrintableObject());
         }
    }

    public void stop(){
         active = false;
    }

    public void run(){
         while (active){
             try {
                 output.printMessage("console_prefix");
                 if (!input.hasNext()){
                     active = false;
                     break;
                 }
                 String command = input.nextLine();
                 if (command.equals("exit")){
                     stop();
                 } else if (command.startsWith("execute_script ")) {
                     String path = command.replace("execute_script", "").trim();
                     if (!paths.contains(path)){
                         paths.add(path);
                         new Client(this, path).run();
                         paths.remove(paths.size()-1);
                     }
                 } else if (command.startsWith("insert") || command.startsWith("update") ||
                         command.startsWith("replace_if_lowe") ||
                         command.startsWith("remove_greater") || command.startsWith("remove_lower")
                 ) {
                     WorkerForm workerForm = new WorkerForm(input,output);
                     executeCommand(command, workerForm.getWorker());
                 }else {
                     executeCommand(command);
                 }
             }catch (TransferException ignore){
                 output.printMessage("crushedServer");
             }
         }
    }

    public static void main(String[] args) {
        if (args.length == 0){
            new Client().run();
        } else if (args.length == 1) {
            new Client(Integer.parseInt(args[0])).run();
        }else {
            new Client(Integer.parseInt(args[0]), args[1]).run();
        }
    }
}
