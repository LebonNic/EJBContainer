package fr.isima.ejbcontainer.normalTestClasses;

public interface INorfBean {
    public void businessMethod();
    public void save(Object entity);
    public void delete(Object entity);
    public boolean isPostConstructMethodCalled();
}
