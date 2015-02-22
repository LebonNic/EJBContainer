package fr.isima.ejbcontainer.persistence.command;

public class PersistCommand implements Command{

    Object entityToPersist;

    public PersistCommand(Object entityToPersist){
        assert entityToPersist != null;
        this.entityToPersist = entityToPersist;
    }

    @Override
    public void execute() {
        //Here, put the stuff in order to persist the entity in the database
    }

    @Override
    public void cancel() {
        //Here, put the stuff in order to cancel the save of the entity in the database
    }
}
