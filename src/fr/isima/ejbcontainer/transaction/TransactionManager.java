package fr.isima.ejbcontainer.transaction;

import java.util.Stack;

public interface TransactionManager {
    public void start();
    public void end();
    public void rollback();
    public Transaction getCurrentTransaction();
    public Stack<Transaction> getCurrentTransactionStack();
}
