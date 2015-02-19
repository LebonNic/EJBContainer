package fr.isima.ejbcontainer.exceptions;

public class SingletonInstantiationFailed extends RuntimeException {
    public SingletonInstantiationFailed(){
        super();
    }

    public SingletonInstantiationFailed(String message){
        super(message);
    }
}
