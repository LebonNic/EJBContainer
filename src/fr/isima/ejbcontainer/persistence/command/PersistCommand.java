package fr.isima.ejbcontainer.persistence.command;

public class PersistCommand implements Command{

    Object entityToPersist;
    private static int persistCommandCount = 0;

    public PersistCommand(Object entityToPersist){
        PersistCommand.persistCommandCount++;
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

    public static int getPersistCommandCount(){
        return PersistCommand.persistCommandCount;
    }
}
