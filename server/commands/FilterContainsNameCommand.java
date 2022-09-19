package server.commands;

import client.net.Request;
import org.jetbrains.annotations.NotNull;
import client.models.Filter;
import server.net.ClientConnection;
import client.models.WorkerCollection;

public class FilterContainsNameCommand extends CommandAbstract {
    final Request request;
    public FilterContainsNameCommand(WorkerCollection workerCollection, ClientConnection clientConnection, Request request) {
        super(workerCollection, clientConnection);
        this.request = request;
    }


    protected Filter getFilter() {
        String string = getMatchGroup(1);
        return worker -> worker.getName().contains(string);
    }

    @Override
    protected @NotNull String getCommandRegex(){
        return "filter_contains_name \\s+(.+)";
    }

    @Override
    public void execute() {
        if (request.getAttr(1).isEmpty()){
            printMessage("incorrectAttr");
        }else {
            printObject(workerCollection.filter(worker -> worker.getName().contains(request.getAttr(1))));
        }
    }

}
