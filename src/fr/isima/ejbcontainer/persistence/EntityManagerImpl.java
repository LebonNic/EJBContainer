package fr.isima.ejbcontainer.persistence;

import fr.isima.ejbcontainer.persistence.command.PersistCommand;
import fr.isima.ejbcontainer.persistence.command.RemoveCommand;
import fr.isima.ejbcontainer.transaction.TransactionManagerImpl;

public class EntityManagerImpl implements EntityManager{
    @Override
    public <T> T persist(T entity) {
        PersistCommand command  = new PersistCommand(entity);
        TransactionManagerImpl.getInstance().getCurrentTransaction().storeAndExecuteCommand(command);
        return entity;
    }

    @Override
    public <T> T remove(T entity) {
        RemoveCommand command = new RemoveCommand(entity);
        TransactionManagerImpl.getInstance().getCurrentTransaction().storeAndExecuteCommand(command);
        return entity;
    }
}
