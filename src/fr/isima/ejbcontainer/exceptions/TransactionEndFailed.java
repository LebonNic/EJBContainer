package fr.isima.ejbcontainer.exceptions;

public class TransactionEndFailed extends RuntimeException {
    public TransactionEndFailed(){
        super();
    }

    public TransactionEndFailed(String message){
        super(message);
    }
}
