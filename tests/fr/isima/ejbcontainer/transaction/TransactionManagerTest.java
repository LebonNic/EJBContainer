package fr.isima.ejbcontainer.transaction;

import fr.isima.ejbcontainer.EJBContainer;
import fr.isima.ejbcontainer.exceptions.TransactionEndFailed;
import fr.isima.ejbcontainer.exceptions.TransactionRollbackFailed;
import fr.isima.ejbcontainer.normalTestClasses.INorfBean;
import org.junit.Test;

import static org.junit.Assert.*;

public class TransactionManagerTest {
    @Test
    public void testTransactionStart(){
        TransactionManagerImpl.getInstance().start();
        TransactionManager transactionManager = TransactionManagerImpl.getInstance();

        assertNotNull(transactionManager);
        assertNotNull(transactionManager.getCurrentTransactionStack());
        assertEquals(transactionManager.getCurrentTransactionStack().size(), 1);

        // Avoid side effects for the other tests
        TransactionManagerImpl.getInstance().end();
    }

    @Test
    public void testTransactionEnd(){
        TransactionManagerImpl.getInstance().start();
        TransactionManagerImpl.getInstance().end();
        TransactionManager transactionManager = TransactionManagerImpl.getInstance();

        assertNotNull(transactionManager);
        assertNotNull(transactionManager.getCurrentTransactionStack());
        assertEquals(transactionManager.getCurrentTransactionStack().size(), 0);
    }

    @Test
    public void testTransactionRollback(){
        TransactionManagerImpl.getInstance().start();
        TransactionManagerImpl.getInstance().rollback();
        TransactionManager transactionManager = TransactionManagerImpl.getInstance();

        assertNotNull(transactionManager);
        assertNotNull(transactionManager.getCurrentTransactionStack());
        assertEquals(transactionManager.getCurrentTransactionStack().size(), 0);
    }

    @Test
    public void testStartSeveralTransaction(){
        TransactionManagerImpl.getInstance().start();
        TransactionManagerImpl.getInstance().start();
        TransactionManagerImpl.getInstance().rollback();
        TransactionManagerImpl.getInstance().start();
        TransactionManagerImpl.getInstance().end();
        TransactionManager transactionManager = TransactionManagerImpl.getInstance();

        assertNotNull(transactionManager);
        assertNotNull(transactionManager.getCurrentTransactionStack());
        assertEquals(transactionManager.getCurrentTransactionStack().size(), 1);

        // Avoid side effects for the other tests
        TransactionManagerImpl.getInstance().end();
    }

    @Test(expected = TransactionEndFailed.class)
    public void testEndATransactionBeforeToStartIt(){
        TransactionManagerImpl.getInstance().end();
    }

    @Test(expected = TransactionRollbackFailed.class)
    public void testRollbackATransactionBeforeToStartIt(){
        TransactionManagerImpl.getInstance().rollback();
    }

    @Test
    public void testHistoryOfCommandsInCurrentTransaction(){
        INorfBean bean = EJBContainer.getInstance().createBean(INorfBean.class);
        int initialCommandsCount, finalCommandsCount;
        TransactionImpl transaction;
        Object mockedObject = new Object();

        //This is an hack for the test, a transaction should normally be started by the InvocationHandler
        TransactionManagerImpl.getInstance().start();
        transaction = (TransactionImpl) TransactionManagerImpl.getInstance().getCurrentTransaction();

        initialCommandsCount = transaction.getHistoryOfCommandCount();
        bean.save(mockedObject);
        bean.save(mockedObject);
        bean.delete(mockedObject);
        finalCommandsCount = transaction.getHistoryOfCommandCount();

        //This is an hack for the test, a transaction should normally be ended by the InvocationHandler
        TransactionManagerImpl.getInstance().end();

        assertEquals(finalCommandsCount - initialCommandsCount, 3);
    }
}