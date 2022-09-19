package server.commands;

import client.net.Request;
import server.net.ClientConnection;
import org.jetbrains.annotations.NotNull;
import client.models.WorkerCollection;


public class CountByStartDate extends CommandAbstract {
    Request request;
    public CountByStartDate(WorkerCollection workerCollection, ClientConnection clientConnection, Request request) {
        super(workerCollection, clientConnection);
        this.request = request;
    }

    @Override
    public void execute() {
        int countDate = 0;
        String string = getMatchGroup(1);
        for(String key : workerCollection.keySet()){
            if(string.equals(workerCollection.getWorker(key).getStartDate().toString())){
                countDate = countDate+1;
            }
        }
        if(countDate > 0){
            printMessage("countByStartDate");
            System.out.println(countDate + "\n");
        }else {
            printMessage("countByStartDate_fail");
        }
    }

    @Override
    protected @NotNull String getCommandRegex() {
        return "count_by_start_date\\s+(.+)";
    }
}
