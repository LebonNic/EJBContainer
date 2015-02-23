package fr.isima.ejbcontainer.utils;

import fr.isima.ejbcontainer.EJBContainer;
import fr.isima.ejbcontainer.normalTestClasses.INorfBean;
import org.junit.Test;

import static org.junit.Assert.*;

public class BeanOperationManagerTest {
    @Test
    public void testPostContructCall(){
        INorfBean bean = EJBContainer.getInstance().createBean(INorfBean.class);
        assertTrue(bean.isPostConstructMethodCalled());
    }
}