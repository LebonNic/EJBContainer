package fr.isima.ejbcontainer.normalTestClasses;

import fr.isima.ejbcontainer.annotations.EJB;
import fr.isima.ejbcontainer.annotations.PersistenceContext;
import fr.isima.ejbcontainer.persistence.EntityManager;

public class BusinessServices {

    @PersistenceContext(unitName = "mock")
    private EntityManager privateEntityManager = null;

    @PersistenceContext(unitName = "mock")
    protected EntityManager protectedEntityManager = null;

    @PersistenceContext(unitName = "mock")
    public EntityManager publicEntityManager = null;

    @EJB
    private IBean anEJBWithPrivateAccessibility = null;

    @EJB
    public IBean anEJBWithPublicAccessibility = null;

    @EJB
    protected IBean anEJBWithProtectedAccessibility = null;

    public IBean getPrivateEJB() {
        return this.anEJBWithPrivateAccessibility;
    }

    public IBean getProtectedEJB() {
        return this.anEJBWithProtectedAccessibility;
    }

    public EntityManager getProtectedEntityManager(){return this.protectedEntityManager;}

    public EntityManager getPrivateEntityManager(){return this.privateEntityManager;}
}
