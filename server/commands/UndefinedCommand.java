package server.commands;

import client.models.WorkerCollection;
import server.net.ClientConnection;

public class UndefinedCommand extends CommandAbstract {
    public UndefinedCommand(WorkerCollection workerCollection, ClientConnection clientConnection) {
        super(workerCollection, clientConnection);
    }

    @Override
    public void execute() {
        printMessage("unavailable_command");
    }
}
