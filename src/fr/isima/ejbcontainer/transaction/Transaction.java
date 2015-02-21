package fr.isima.ejbcontainer.transaction;

public interface Transaction {
    public void commit();
    public void rollback();
}
