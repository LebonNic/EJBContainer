package fr.isima.ejbcontainer;

import com.google.common.reflect.AbstractInvocationHandler;
import fr.isima.ejbcontainer.annotations.TransactionAttribute;
import fr.isima.ejbcontainer.annotations.TransactionAttributeType;
import fr.isima.ejbcontainer.exceptions.MethodInvocationFailed;
import fr.isima.ejbcontainer.transaction.TransactionManagerImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InvocationHandler extends AbstractInvocationHandler {
    private static final Logger LOG = Logger.getLogger(InvocationHandler.class.getName());
    private InstanceManager instanceManager;
    private Class<?> beanInterface;
    private Map<Long, Boolean> threadIdToHasStartedTransaction = new HashMap<>();

    public InvocationHandler(InstanceManager instanceManager, Class<?> beanInterface) {
        this.instanceManager = instanceManager;
        this.beanInterface = beanInterface;
    }

    @Override
    protected Object handleInvocation(Object o, Method method, Object[] args) throws Throwable {
        LOG.log(Level.INFO, "Executes method \"{0}\" from \"{1}\" with arguments \"{2}\".",
                new Object[]{method.getName(), this.beanInterface.getName(), args});

        Object bean = this.instanceManager.getInstance(this.beanInterface);
        TransactionAttributeType transactionType = getTransactionAttributeType(bean, method);
        Object result;

        handleTransactionBeforeInvoke(transactionType);
        try {
            result = method.invoke(bean, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            TransactionManagerImpl.getInstance().rollback();
            throw new MethodInvocationFailed("An error occurred during the call of the method \"" + method.getName()
                    + "\" from \"" + this.beanInterface + "\". Detail explanations: " + e.getMessage() + ".");
        }
        handleTransactionAfterInvoke(transactionType);
        this.instanceManager.freeInstance(beanInterface, bean);

        return result;
    }

    private void handleTransactionBeforeInvoke(TransactionAttributeType type){
        if(type == TransactionAttributeType.REQUIRED){
            if(TransactionManagerImpl.getInstance().getCurrentTransaction() == null){
                TransactionManagerImpl.getInstance().start();
                this.threadIdToHasStartedTransaction.put(Thread.currentThread().getId(), Boolean.TRUE);
            }
            else {
                this.threadIdToHasStartedTransaction.put(Thread.currentThread().getId(), Boolean.FALSE);
            }
        }
        else if(type == TransactionAttributeType.REQUIRES_NEW){
            TransactionManagerImpl.getInstance().start();
        }
    }

    private void handleTransactionAfterInvoke(TransactionAttributeType type){
        if(type == TransactionAttributeType.REQUIRED){
            if(this.threadIdToHasStartedTransaction.get(Thread.currentThread().getId()) == Boolean.TRUE){
                TransactionManagerImpl.getInstance().end();
            }
            this.threadIdToHasStartedTransaction.remove(Thread.currentThread().getId());
        }
        else if (type == TransactionAttributeType.REQUIRES_NEW){
            TransactionManagerImpl.getInstance().end();
        }
    }

    private TransactionAttributeType getTransactionAttributeType(Object bean, Method method) {
        if (method.isAnnotationPresent(TransactionAttribute.class)) {
            return method.getAnnotation(TransactionAttribute.class).value();
        } else if (bean.getClass().isAnnotationPresent(TransactionAttribute.class)) {
            return bean.getClass().getAnnotation(TransactionAttribute.class).value();
        } else {
            return TransactionAttributeType.REQUIRED;
        }
    }
}
