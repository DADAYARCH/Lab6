package server.commands;

import client.net.Request;
import server.net.ClientConnection;
import client.models.Worker;
import client.models.WorkerCollection;

public class UpdateCommand extends CommandAbstract {
    final Request request;

    public UpdateCommand(WorkerCollection workerCollection, ClientConnection clientConnection, Request request) {
        super(workerCollection, clientConnection);
        this.request = request;
    }

    private boolean updateWorker(Worker newWorker){
        Worker oldWorker = workerCollection.getWorkerById(getID());
        if (oldWorker != null){
            oldWorker.setName(newWorker.getName());
            oldWorker.setStartDate(newWorker.getStartDate());
            oldWorker.setSalary(newWorker.getSalary());
            oldWorker.setCreationDate(newWorker.getCreationDate());
            oldWorker.setCoordinates(newWorker.getCoordinates());
            oldWorker.setOrganization(newWorker.getOrganization());
            oldWorker.setPosition(newWorker.getPosition());
            oldWorker.setStatus(newWorker.getStatus());
            return true;
        }else {
            return false;
        }
    }

    protected Integer getID() {
        Integer id = null;
        if (!request.getAttr(1).isEmpty()){
            try {
                id = Integer.parseInt(request.getAttr(1));
            }catch (NumberFormatException ignore){}
        }
        return id;
    }

   public void execute(){
        if (getID()==null){
            printMessage("incorrectId");
        }else {
            Worker worker = request.getWorker();
            if (worker!=null){
                if (updateWorker(worker)){
                    printMessage("updateCommand_wasUpdate");
                }else {
                    printMessage("incorrectId");
                }
            }else {
                printMessage("workerForm_isInvalid");
            }
        }
   }
}

