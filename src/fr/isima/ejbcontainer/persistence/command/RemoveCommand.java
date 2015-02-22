package fr.isima.ejbcontainer.persistence.command;

public class RemoveCommand implements Command{

    Object entityToRemove;

    public RemoveCommand(Object entityToRemove){
        assert entityToRemove != null;
        this.entityToRemove = entityToRemove;
    }

    @Override
    public void execute() {
        //Here, put the stuff in order to remove the entity in the database
    }

    @Override
    public void cancel() {
        //Here, put the stuff in order to cancel the withdrawal of the entity in the database
    }
}
