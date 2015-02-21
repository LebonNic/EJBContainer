package fr.isima.ejbcontainer.exceptions;

public class TransactionStackNotFound extends Exception{
    public TransactionStackNotFound(){
        super();
    }

    public TransactionStackNotFound(String message){
        super(message);
    }
}
