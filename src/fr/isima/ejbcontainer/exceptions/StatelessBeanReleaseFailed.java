package fr.isima.ejbcontainer.exceptions;

public class StatelessBeanReleaseFailed extends RuntimeException {
    public StatelessBeanReleaseFailed(){
        super();
    }

    public StatelessBeanReleaseFailed(String message){
        super(message);
    }
}
