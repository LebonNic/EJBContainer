package fr.isima.ejbcontainer.exceptions;

public class MethodInvocationFailed extends RuntimeException {
    public MethodInvocationFailed() {

    }

    public MethodInvocationFailed(String message) {
        super(message);
    }
}
