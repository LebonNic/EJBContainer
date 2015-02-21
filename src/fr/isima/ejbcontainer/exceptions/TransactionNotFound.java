package fr.isima.ejbcontainer.exceptions;

public class TransactionNotFound extends Exception {
    public TransactionNotFound(){
        super();
    }

    public TransactionNotFound(String message){
        super(message);
    }
}
