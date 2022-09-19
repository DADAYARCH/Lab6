package server.commands;

import client.net.Request;
import server.net.ClientConnection;
import client.models.Worker;
import client.models.WorkerCollection;

public class RemoveLowerCommand extends CommandAbstract {
    final Request request;
    public RemoveLowerCommand(WorkerCollection workerCollection, ClientConnection clientConnection, Request request) {
        super(workerCollection, clientConnection);
        this.request = request;
    }

    public void execute() {
        Worker worker = request.getWorker();
        if (worker != null){
            WorkerCollection workerForRemove = workerCollection.filter(w -> w.isLowerThan(worker));
            for (String key : workerForRemove.keySet()){
                workerCollection.remove(key);
            }
            printMessage("removeCommand");
        }else {
            printMessage("workerForm_isInvalid");
        }
    }
}
