package fr.isima.ejbcontainer.transaction;

import fr.isima.ejbcontainer.persistence.command.Command;

import java.util.ArrayList;
import java.util.List;

public class TransactionImpl implements Transaction{

    private static int instanceCount = 0;
    private List<Command> historyOfCommands;

    public TransactionImpl(){
        TransactionImpl.instanceCount += 1;
        this.historyOfCommands = new ArrayList<>();
    }

    @Override
    public void commit() {

    }

    @Override
    public void rollback() {
        for(Command command : this.historyOfCommands){
            command.cancel();
        }
    }

    @Override
    public void storeAndExecuteCommand(Command command) {
        command.execute();
        this.historyOfCommands.add(command);
    }

    public static int getInstanceCount(){
        return TransactionImpl.instanceCount;
    }

    public int getHistoryOfCommandCount(){
        return this.historyOfCommands.size();
    }
}
