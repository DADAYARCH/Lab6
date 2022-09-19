package server.commands;

import server.net.ClientConnection;
import client.models.WorkerCollection;

public class ShowCommand extends CommandAbstract {
    public ShowCommand(WorkerCollection workerCollection, ClientConnection clientConnection) {
        super(workerCollection, clientConnection);
    }

    @Override
    public void execute() {
        if (workerCollection.getWorkers().isEmpty()){
            printMessage("showCommand_emptyCollection");
        } else{
            printObject(workerCollection.getWorkers());
        }
    }
}
