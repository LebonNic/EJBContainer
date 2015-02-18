package fr.isima.ejbcontainer;

import fr.isima.ejbcontainer.errorTestClasses.IBarBean;
import fr.isima.ejbcontainer.exceptions.IncoherentAnnotationsUsage;
import fr.isima.ejbcontainer.normalTestClasses.IBean;
import org.junit.Test;

import static org.junit.Assert.*;

public class EJBContainerTest {

    @Test
    public void testEJBContainerCreation(){
        assertNotNull(EJBContainer.getInstance().getSingletonInstanceManager());
        // TODO adds the same test for Stateless EJB
    }

    @Test
    public void testSingletonBeanProxyCreation(){
        IBean bean = EJBContainer.getInstance().createBean(IBean.class);
        assertNotNull(bean);
    }

    @Test(expected = IncoherentAnnotationsUsage.class)
    public void testIncoherentAnnotationsUsage(){
        EJBContainer.getInstance().createBean(IBarBean.class);
    }
}