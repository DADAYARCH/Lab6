package server.commands;

import server.net.ClientConnection;
import client.models.WorkerCollection;

public class ClearCommand extends CommandAbstract {
    public ClearCommand(WorkerCollection workerCollection,ClientConnection clientConnection) {
        super(workerCollection, clientConnection);
    }

    @Override
    public void execute() {
        workerCollection.clear();
        printMessage("collection_is_clear");
    }
}
