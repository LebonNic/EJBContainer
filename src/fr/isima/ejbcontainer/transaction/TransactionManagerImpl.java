package fr.isima.ejbcontainer.transaction;

import fr.isima.ejbcontainer.exceptions.TransactionEndFailed;
import fr.isima.ejbcontainer.exceptions.TransactionNotFound;
import fr.isima.ejbcontainer.exceptions.TransactionRollbackFailed;
import fr.isima.ejbcontainer.exceptions.TransactionStackNotFound;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransactionManagerImpl implements TransactionManager{
    private static final Logger LOG = Logger.getLogger(TransactionManager.class.getName());
    private static TransactionManagerImpl transactionManager = null;
    private Map<Long, Stack<Transaction>> threadToTransactionStack = new HashMap<>();

    private TransactionManagerImpl(){
    }

    public static TransactionManager getInstance(){
        if(TransactionManagerImpl.transactionManager == null){
            synchronized (TransactionManagerImpl.class){
                if (TransactionManagerImpl.transactionManager == null){
                    LOG.log(Level.INFO, "Creation of the transaction manager.");
                    TransactionManagerImpl.transactionManager = new TransactionManagerImpl();
                }
            }
        }
        return TransactionManagerImpl.transactionManager;
    }

    @Override
    public void start() {
        LOG.log(Level.INFO, "Thread " + Thread.currentThread().getId() + " is starting a new transaction.");
        Long threadId = Thread.currentThread().getId();
        Transaction newTransaction = new TransactionImpl();
        Stack<Transaction> transactionStack = getTransactionStackForCurrentThread();

        if(transactionStack == null){
            LOG.log(Level.INFO, "Creating a new transaction stack for the thread " + threadId + ".");
            transactionStack = new Stack<>();
            this.threadToTransactionStack.put(threadId, transactionStack);
        }
        transactionStack.push(newTransaction);
    }

    @Override
    public void end() {
        LOG.log(Level.INFO, "Thread " + Thread.currentThread().getId() + " is ending a transaction.");
        Transaction transaction;

        try {
            transaction = popTransactionForCurrentThread();
        } catch (TransactionNotFound | TransactionStackNotFound e) {
            throw new TransactionEndFailed("The end of the transaction failed for the thread " +
                    Thread.currentThread().getId() + ". Normally it's due to the fact that the thread has not started" +
                    "a transaction before to ask to end it.");
        }
        transaction.commit();
    }

    @Override
    public void rollback() {
        LOG.log(Level.INFO, "Thread " + Thread.currentThread().getId() + " is rollbacking a transaction.");
        Transaction transaction;

        try {
            transaction = popTransactionForCurrentThread();
        } catch (TransactionNotFound | TransactionStackNotFound e) {
            throw new TransactionRollbackFailed("The end of the transaction failed for the thread "+
                    Thread.currentThread().getId() + ". Normally it's due to the fact that the thread has not started " +
                    "a transaction before to ask to roll it back.");
        }
        transaction.rollback();
    }

    @Override
    public Transaction getCurrentTransaction() {
        Transaction transaction = null;
        Stack<Transaction> transactionStack = getTransactionStackForCurrentThread();

        if(transactionStack != null){
            if(!transactionStack.isEmpty()){
                transaction = transactionStack.peek();
            }
        }
        return transaction;
    }

    @Override
    public Stack<Transaction> getCurrentTransactionStack(){
        return getTransactionStackForCurrentThread();
    }

    private Stack<Transaction> getTransactionStackForCurrentThread(){
        Long threadId = Thread.currentThread().getId();
        return this.threadToTransactionStack.get(threadId);
    }

    private Transaction popTransactionForCurrentThread() throws TransactionNotFound, TransactionStackNotFound {
        Transaction transaction;
        Stack<Transaction> transactionStack = getTransactionStackForCurrentThread();

        if(transactionStack != null){
            if(!transactionStack.isEmpty()){
                transaction = transactionStack.pop();
            }
            else{
                throw new TransactionNotFound("No transaction were found in the transaction stack of the thread " +
                        + Thread.currentThread().getId() + ".");
            }
        }
        else{
            throw new TransactionStackNotFound("No transaction stack was found for the thread " +
                    Thread.currentThread().getId() + ".");
        }
        return transaction;
    }
}
