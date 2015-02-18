package fr.isima.ejbcontainer;

public interface InstanceManager {
    public <T> T getInstance(Class<T> beanInterface);
}
