package fr.isima.ejbcontainer.persistence.command;

public class RemoveCommand implements Command{

    Object entityToRemove;
    private static int removeCommandCount = 0;

    public RemoveCommand(Object entityToRemove){
        assert entityToRemove != null;
        this.entityToRemove = entityToRemove;
        RemoveCommand.removeCommandCount++;
    }

    @Override
    public void execute() {
        //Here, put the stuff in order to remove the entity in the database
    }

    @Override
    public void cancel() {
        //Here, put the stuff in order to cancel the withdrawal of the entity in the database
    }

    public static int getRemoveCommandCount(){
        return RemoveCommand.removeCommandCount;
    }
}
