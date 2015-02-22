package fr.isima.ejbcontainer.transaction;

import fr.isima.ejbcontainer.persistence.command.Command;

public interface Transaction {
    public void commit();
    public void rollback();
    public void storeAndExecuteCommand(Command command);
}
