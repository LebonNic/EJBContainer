package fr.isima.ejbcontainer.exceptions;

public class InjectionFailed extends RuntimeException {
    public InjectionFailed() {
        super();
    }

    public InjectionFailed(String message) {
        super(message);
    }
}
