package fr.isima.ejbcontainer.transaction;

import fr.isima.ejbcontainer.EJBContainer;
import fr.isima.ejbcontainer.normalTestClasses.IBazBean;
import fr.isima.ejbcontainer.normalTestClasses.IBean;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RuntimeTransactionManagementTest {

    @Test
    public void testRequiredTransactionMethod(){
        int initialTransactionCount, finalTransactionCount;
        IBazBean bean = EJBContainer.getInstance().createBean(IBazBean.class);

        initialTransactionCount = TransactionImpl.instanceCount;
        bean.businessMethod();
        finalTransactionCount = TransactionImpl.instanceCount;

        TransactionManager transactionManager = TransactionManagerImpl.getInstance();
        assertNotNull(transactionManager);
        assertNotNull(transactionManager.getCurrentTransactionStack());
        assertEquals(transactionManager.getCurrentTransactionStack().size(), 0);
        assertEquals(finalTransactionCount - initialTransactionCount, 1);
    }

    @Test
    public void testRequiresNewTransactionMethods(){
        int initialTransactionCount, finalTransactionCount;
        IBean bean = EJBContainer.getInstance().createBean(IBean.class);

        initialTransactionCount = TransactionImpl.instanceCount;
        bean.businessMethod();
        finalTransactionCount = TransactionImpl.instanceCount;

        TransactionManager transactionManager = TransactionManagerImpl.getInstance();
        assertNotNull(transactionManager);
        assertNotNull(transactionManager.getCurrentTransactionStack());
        assertEquals(transactionManager.getCurrentTransactionStack().size(), 0);
        assertEquals(finalTransactionCount - initialTransactionCount, 2);
    }
}
