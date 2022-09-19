package server.commands;

import server.net.ClientConnection;

import client.models.WorkerCollection;

import java.util.LinkedHashMap;

public class InfoCommand extends CommandAbstract {
    public InfoCommand(WorkerCollection workerCollection, ClientConnection clientConnection) {
        super(workerCollection,clientConnection);
    }

    @Override
    public void execute() {
        LinkedHashMap<String, String> info = new LinkedHashMap<>();
        info.put("Количество элементов", ((Integer) workerCollection.getSize()).toString());
        printObject(info);
    }
}
