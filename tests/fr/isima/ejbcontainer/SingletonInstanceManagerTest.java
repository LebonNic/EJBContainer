package fr.isima.ejbcontainer;

import fr.isima.ejbcontainer.normalTestClasses.IBean;
import org.junit.Test;

import static org.junit.Assert.*;

public class SingletonInstanceManagerTest {

    @Test
    public void testSingletonBeanCreation() {
        IBean bean = EJBContainer.getInstance().createBean(IBean.class);
        bean.businessMethod();
        bean.businessMethod();
        assertEquals(EJBContainer.getInstance().getSingletonInstanceManager().getInstanceCount(), 1);
    }
}