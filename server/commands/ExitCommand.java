package server.commands;

import client.models.ParsingError;
import server.net.ClientConnection;
import client.models.WorkerCollection;

public class ExitCommand extends CommandAbstract {
    public ExitCommand(WorkerCollection workerCollection, ClientConnection clientConnection) {
        super(workerCollection,clientConnection);
    }

    @Override
    public void execute() {
        try{
            workerCollection.dumpToJson();
        }catch (ParsingError ignore){}
    }
}
