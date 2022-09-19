package server.commands;

import server.net.ClientConnection;
import client.models.WorkerCollection;

public class HelpCommand extends CommandAbstract {
    public HelpCommand(WorkerCollection workerCollection, ClientConnection clientConnection) {
        super(workerCollection, clientConnection);
    }

    @Override
    public void execute() {
        printMessage("helpCommand_text");
    }
}
