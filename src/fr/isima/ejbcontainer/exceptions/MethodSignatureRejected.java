package fr.isima.ejbcontainer.exceptions;

public class MethodSignatureRejected extends RuntimeException {
    public MethodSignatureRejected(){
        super();
    }

    public MethodSignatureRejected(String message){
        super(message);
    }
}
