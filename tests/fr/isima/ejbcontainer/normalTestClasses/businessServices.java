package fr.isima.ejbcontainer.normalTestClasses;

import fr.isima.ejbcontainer.annotations.EJB;

public class BusinessServices {
    @EJB
    private IBean anEJBWithPrivateAccessibility = null;

    @EJB
    public IBean anEJBWithPublicAccessibility = null;

    @EJB
    protected IBean anEJBWithProtectedAccessibility = null;

    public IBean getPrivateEJB(){
        return this.anEJBWithPrivateAccessibility;
    }

    public IBean getProtectedEJB(){
        return this.anEJBWithProtectedAccessibility;
    }
}
