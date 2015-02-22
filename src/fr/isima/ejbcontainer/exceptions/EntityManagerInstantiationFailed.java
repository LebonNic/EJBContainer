package fr.isima.ejbcontainer.exceptions;

public class EntityManagerInstantiationFailed extends RuntimeException{
    public EntityManagerInstantiationFailed(){
        super();
    }

    public EntityManagerInstantiationFailed(String message){
        super(message);
    }
}
