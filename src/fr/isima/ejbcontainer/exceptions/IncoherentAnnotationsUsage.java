package fr.isima.ejbcontainer.exceptions;

public class IncoherentAnnotationsUsage extends RuntimeException {
    public IncoherentAnnotationsUsage() {
        super();
    }

    public IncoherentAnnotationsUsage(String message) {
        super(message);
    }
}
