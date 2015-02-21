package fr.isima.ejbcontainer.transaction;

public class TransactionImpl implements Transaction{

    public static int instanceCount = 0;

    public TransactionImpl(){
        TransactionImpl.instanceCount += 1;
    }

    @Override
    public void commit() {

    }

    @Override
    public void rollback() {

    }
}
