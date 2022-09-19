package client.net;

import client.models.Worker;

import java.io.Serializable;

public class Request implements Serializable {
    private String[] command;
    private Worker worker;

    public Request(String userInput){
        this.command = userInput.split("\\s+");
    }

    public Request(String userInput, Worker worker){
        this.command = userInput.split("\\s+");
        this.worker = worker;
    }

    public Worker getWorker() {
        return worker;
    }

    public String getCommand() {
        return command[0];
    }

    public String getAttr(int position){
        if (position < command.length){
            return command[position];
        }else {
            return "";
        }
    }
}
