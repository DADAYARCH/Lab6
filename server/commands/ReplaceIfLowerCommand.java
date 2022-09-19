package server.commands;

import client.net.Request;
import server.net.ClientConnection;
import client.models.Worker;
import client.models.WorkerCollection;

import java.util.stream.Stream;

public class ReplaceIfLowerCommand extends CommandAbstract {
    final Request request;

    public ReplaceIfLowerCommand(WorkerCollection workerCollection, ClientConnection clientConnection, Request request) {
        super(workerCollection, clientConnection);
        this.request = request;
    }

    @Override
    public void execute() {
        Worker workerInfo = request.getWorker();
        if (workerInfo != null) {
            Stream<Worker> workerStream = workerCollection.getWorkers().values().stream();
            workerStream.forEach(worker -> {
                if (worker.isLowerThan(workerInfo)) {
                    worker.setName(workerInfo.getName());
                    worker.setCoordinates(workerInfo.getCoordinates());
                    worker.setCreationDate(workerInfo.getCreationDate());
                    worker.setSalary(workerInfo.getSalary());
                    worker.setStartDate(workerInfo.getStartDate());
                }
            });
            printMessage("success");
        } else {
            printMessage("ticketForm_isInvalid");
        }
    }
}
