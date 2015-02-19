package fr.isima.ejbcontainer.exceptions;

public class ImplementationNotFound extends RuntimeException {
    public ImplementationNotFound() {
        super();
    }

    public ImplementationNotFound(String message) {
        super(message);
    }
}
