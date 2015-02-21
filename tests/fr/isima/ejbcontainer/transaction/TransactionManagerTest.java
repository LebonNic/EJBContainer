package fr.isima.ejbcontainer.transaction;

import fr.isima.ejbcontainer.exceptions.TransactionEndFailed;
import fr.isima.ejbcontainer.exceptions.TransactionRollbackFailed;
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
}