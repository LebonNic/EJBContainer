package fr.isima.ejbcontainer.exceptions;

public class SingletonBeanInstantiationFailed extends RuntimeException {
    public SingletonBeanInstantiationFailed() {
        super();
    }

    public SingletonBeanInstantiationFailed(String message) {
        super(message);
    }
}
