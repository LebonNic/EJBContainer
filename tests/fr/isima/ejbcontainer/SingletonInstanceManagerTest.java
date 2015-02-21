package fr.isima.ejbcontainer;

import fr.isima.ejbcontainer.normalTestClasses.IBean;
import org.junit.Test;

import static org.junit.Assert.*;

public class SingletonInstanceManagerTest {

    @Test
    public void testSingletonBeanCreation() {
        int initialSingletonBeanCount, finalSingletonBeanCount;

        IBean bean = EJBContainer.getInstance().createBean(IBean.class);
        initialSingletonBeanCount = EJBContainer.getInstance().getSingletonInstanceManager().getInstanceCount();
        bean.businessMethod();
        bean.businessMethod();
        finalSingletonBeanCount = EJBContainer.getInstance().getSingletonInstanceManager().getInstanceCount();

        //The value is 2 here because SingletonBean contains an other SingletonBean
        assertEquals(finalSingletonBeanCount - initialSingletonBeanCount, 2);
    }
}