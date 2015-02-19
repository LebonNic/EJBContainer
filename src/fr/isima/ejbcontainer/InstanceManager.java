package fr.isima.ejbcontainer;

public interface InstanceManager {
    public <T> T getInstance(Class<T> beanInterface);

    public <T> void freeInstance(Class<?> beanInterface, T bean);
}
