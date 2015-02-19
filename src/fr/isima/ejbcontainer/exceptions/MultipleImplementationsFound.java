package fr.isima.ejbcontainer.exceptions;

public class MultipleImplementationsFound extends RuntimeException {

    public MultipleImplementationsFound() {
        super();
    }

    public MultipleImplementationsFound(String message) {
        super(message);
    }

}
