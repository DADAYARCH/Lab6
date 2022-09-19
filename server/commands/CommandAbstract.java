package server.commands;


import server.net.ClientConnection;
import client.net.Response;
import org.jetbrains.annotations.NotNull;
import client.models.WorkerCollection;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


abstract public class CommandAbstract implements Command {
    protected WorkerCollection workerCollection;
    protected ClientConnection clientConnection;
    protected Matcher commandMatcher;

    public CommandAbstract(WorkerCollection workerCollection, ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
        this.workerCollection = workerCollection;
    }

    protected void printMessage(String messageCode) {
        Response response = new Response(messageCode, true);
        clientConnection.sendResponse(response);
    }

    protected void printObject(Serializable object) {
        Response response = new Response(object);
        clientConnection.sendResponse(response);
    }

    public String getMatchGroup(int groupNumber) {
        if (commandMatcher != null) {
            return commandMatcher.group(groupNumber);
        } else {
            throw new RuntimeException("Unexpected Error");
        }
    }

//    public boolean isThisCommand(String string) {
//        Pattern pattern = Pattern.compile(getCommandRegex(), Pattern.CASE_INSENSITIVE);
//        commandMatcher = pattern.matcher(string);
//        return commandMatcher.matches();
//    }

    public String getCommandName() {
        String className = this.getClass().getTypeName();
        Matcher matcher = Pattern.compile("([\\d\\w]+)Command$").matcher(className);
        if (!matcher.find()) {
            throw new RuntimeException("Rename command class or override getCommandName() method");
        }
        String commandName = matcher.group(1);
        commandName = commandName.replaceAll(
                "(?<=[A-Z])(?=[A-Z][a-z])|(?<=[^A-Z])(?=[A-Z])|(?<=[A-Za-z])(?=[^A-Za-z])", "_");
        return commandName.toLowerCase();
    }

    protected @NotNull String getCommandRegex() {
        return getCommandName();
    }


    public abstract void execute();
}
