package fr.isima.ejbcontainer.exceptions;

public class StatelessBeanInstantiationFailed extends RuntimeException {
    public StatelessBeanInstantiationFailed() {
        super();
    }

    public StatelessBeanInstantiationFailed(String message) {
        super(message);
    }
}
