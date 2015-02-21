package fr.isima.ejbcontainer.transaction;

public interface TransactionManager {
    public void start();
    public void end();
    public void rollback();
    public Transaction getCurrentTransaction();
}
