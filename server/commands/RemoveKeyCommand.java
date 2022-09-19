package server.commands;

import client.net.Request;
import server.net.ClientConnection;
import client.models.WorkerCollection;

public class RemoveKeyCommand extends CommandAbstract {
    final Request request;

    public RemoveKeyCommand(WorkerCollection workerCollection, ClientConnection clientConnection, Request request) {
        super(workerCollection, clientConnection);
        this.request = request;
    }

    public void execute() {
        if (request.getAttr(1).isEmpty()) {
            printMessage("incorrectAttr");
        } else {
            workerCollection.remove(request.getAttr(1));
            printMessage("removeCommand");
        }
    }
}
