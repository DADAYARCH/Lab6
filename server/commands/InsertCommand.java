package server.commands;

import client.models.Worker;
import client.net.Request;
import server.net.ClientConnection;
import client.models.WorkerCollection;

public class InsertCommand extends CommandAbstract {
    final Request request;

    public InsertCommand(WorkerCollection workerCollection, ClientConnection clientConnection, Request request){
        super(workerCollection, clientConnection);
        this.request = request;
    }

    public void execute() {
        if (request.getAttr(1).isEmpty()) {
            printMessage("incorrectAttr");
        } else {
            Worker worker = request.getWorker();
            if (worker != null) {
                worker.initialize();
                workerCollection.put(request.getAttr(1), worker);
                printMessage("insertCommand_addedTicket");
            } else {
                printMessage("workerForm_isInvalid");
            }
        }
    }
}
