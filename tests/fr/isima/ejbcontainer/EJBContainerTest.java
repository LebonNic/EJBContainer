package fr.isima.ejbcontainer;

import fr.isima.ejbcontainer.normalTestClasses.BeanInterface;
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
        BeanInterface bean = EJBContainer.getInstance().createBean(BeanInterface.class);
        assertNotNull(bean);
    }
}