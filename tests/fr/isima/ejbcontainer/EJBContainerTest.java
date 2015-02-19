package fr.isima.ejbcontainer;

import fr.isima.ejbcontainer.errorTestClasses.IBarBean;
import fr.isima.ejbcontainer.exceptions.IncoherentAnnotationsUsage;
import fr.isima.ejbcontainer.normalTestClasses.BusinessServices;
import fr.isima.ejbcontainer.normalTestClasses.IBazBean;
import fr.isima.ejbcontainer.normalTestClasses.IBean;
import org.junit.Test;

import static org.junit.Assert.*;

public class EJBContainerTest {

    @Test
    public void testEJBContainerCreation() {
        assertNotNull(EJBContainer.getInstance().getSingletonInstanceManager());
        assertNotNull(EJBContainer.getInstance().getStatelessInstanceManager());
    }

    @Test
    public void testSingletonBeanProxyCreation() {
        IBean bean = EJBContainer.getInstance().createBean(IBean.class);
        assertNotNull(bean);
    }

    @Test
    public void testStatelessBeanProxyCreation() {
        IBazBean bean = EJBContainer.getInstance().createBean(IBazBean.class);
        assertNotNull(bean);
    }

    @Test(expected = IncoherentAnnotationsUsage.class)
    public void testIncoherentAnnotationsUsage() {
        EJBContainer.getInstance().createBean(IBarBean.class);
    }

    @Test
    public void testEJBInjection() {
        BusinessServices services = new BusinessServices();
        EJBContainer.getInstance().manage(services);

        assertNotNull(services.anEJBWithPublicAccessibility);
        assertNotNull(services.getProtectedEJB());
        assertNotNull(services.getPrivateEJB());
    }
}