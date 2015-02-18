package fr.isima.ejbcontainer;

import fr.isima.ejbcontainer.normalTestClasses.BeanInterface;
import org.junit.Test;

import static org.junit.Assert.*;

public class SingletonInstanceManagerTest {

    @Test
    public void testSingletonBeanCreation(){
        BeanInterface bean = EJBContainer.getInstance().createBean(BeanInterface.class);
        bean.businessMethod();
        assertEquals(EJBContainer.getInstance().getSingletonInstanceManager().getInstanceCount(), 1);
    }
}