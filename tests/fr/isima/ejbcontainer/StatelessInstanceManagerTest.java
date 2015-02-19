package fr.isima.ejbcontainer;

import fr.isima.ejbcontainer.normalTestClasses.IBazBean;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.*;

public class StatelessInstanceManagerTest {
    @Test
    public void testStatelessBeanCreation() {
        IBazBean bean = EJBContainer.getInstance().createBean(IBazBean.class);
        bean.businessMethod();
        bean.businessMethod();
        Stack<Object> pool = EJBContainer.getInstance().getStatelessInstanceManager().getImplementationPool(IBazBean.class);
        assertNotNull(pool);
        assertEquals(pool.size(), 1);
    }

}