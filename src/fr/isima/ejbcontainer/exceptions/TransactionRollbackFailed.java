package fr.isima.ejbcontainer.exceptions;

public class TransactionRollbackFailed extends RuntimeException {
    public TransactionRollbackFailed(){
        super();
    }

    public TransactionRollbackFailed(String message){
        super(message);
    }
}
