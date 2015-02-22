package fr.isima.ejbcontainer.persistence.command;

public interface Command {
    public void execute();
    public void cancel();
}
