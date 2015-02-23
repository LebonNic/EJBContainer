package fr.isima.ejbcontainer.persistence;

import fr.isima.ejbcontainer.annotations.PersistenceContext;
import fr.isima.ejbcontainer.persistence.command.PersistCommand;
import fr.isima.ejbcontainer.persistence.command.RemoveCommand;
import fr.isima.ejbcontainer.transaction.TransactionManagerImpl;

import java.util.logging.Level;
import java.util.logging.Logger;

@PersistenceContext(unitName = "mock")
public class EntityManagerImpl implements EntityManager{
    private static final Logger LOG = Logger.getLogger(EntityManagerImpl.class.getSimpleName());

    @Override
    public <T> T persist(T entity) {
        LOG.log(Level.INFO, "Persist an object named \"" + entity.getClass().getSimpleName() + "\".");
        PersistCommand command  = new PersistCommand(entity);
        TransactionManagerImpl.getInstance().getCurrentTransaction().storeAndExecuteCommand(command);
        return entity;
    }

    @Override
    public <T> T remove(T entity) {
        LOG.log(Level.INFO, "Remove an object named \"" + entity.getClass().getSimpleName() + "\".");
        RemoveCommand command = new RemoveCommand(entity);
        TransactionManagerImpl.getInstance().getCurrentTransaction().storeAndExecuteCommand(command);
        return entity;
    }
}
