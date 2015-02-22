package fr.isima.ejbcontainer.persistence;

public interface EntityManager {
    public <T> T persist(T entity);
    public <T> T remove(T entity);
}
